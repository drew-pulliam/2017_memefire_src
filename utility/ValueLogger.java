package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A ValueLogger gathers information from other classes and writes this information to log dump
 * files and/or to the Smart Dashboard.
 * <p>
 * The intended usage of the class is as follows.
 * 1. Classes that have useful information available implement the LogDataSource interface.
 * 2. The main Robot class creates a ValueLogger in robotInit() and registers the other singletons,
 *    such as Subsystems, Commands, the OI class, etc.
 * 3. In its disabledInit(), autonomousInit(), and teleopInit() methods, the main Robot class
 *    calls its ValueLogger's initializePhase() method, passing the appropriate String constant
 *    DISABLED_PHASE, AUTONOMOUS_PHASE, or TELEOP_PHASE.
 * 4. In its autonomousPeriodic() and teleopPeriodic() methods, the main Robot class starts by
 *    recording the current time, and at the end calls its ValueLogger's logValues() method. For
 *    example...
 *      long start_phase = System.nanoTime();
 *      Scheduler.getInstance().run();
 *      logValues(start_phase);
 * <p>
 * OK, so if I do all that, how do I benefit?
 * <p>
 * There are two main benefits to this approach.
 * First, and probably most helpful, is that after every autonomous or teleop phase your Robot
 * runs, you will have a .csv file with boatloads of useful data gathered during operations. In
 * addition to the values you log from Subsystems and Commands, you'll know how long your code
 * is taking to run, by comparing the nanoTime() values in the first two columns.
 * Second, you can control the decision of what to display on the Smart Dashboard in the
 * configuration file, so you don't have to change code to change what is being displayed on
 * the Smart Dashboard and what is being logged in the .csv file.
 * <p>
 * Also, the process of gathering information from the various Robot classes and recording it is
 * abstracted away, so that at each point in the process, you can focus on what needs to be
 * done, and what would help in debugging problems and optimizing performance, rather than on
 * how the information will be gathered and displayed. As a side effect, dependencies between
 * classes are reduced, and this makes code reuse in future Robots easier.
 */
public class ValueLogger {
	public static final String
		DISABLED_PHASE		= "DISABLED"
	  , AUTONOMOUS_PHASE	= "AUTONOMOUS"
	  , TELEOP_PHASE		= "TELEOP"
	;

	private List<LogDataSource> m_sourceList;
	private int                 m_maxLogFiles;
	private String              m_filePathBase;

	/**
	 * This constructor has no side effects; it just sets up the ValueLogger for use.
	 * 
	 * @param filePathBase  the base path for log dump files, and also for the configuration file 
	 * @param maxLogFiles   the maximum number of log dump files before the numbers roll over
	 */
	public ValueLogger ( String filePathBase, int maxLogFiles )
	{
		m_sourceList   = new LinkedList<LogDataSource>();
		m_filePathBase = filePathBase;
		m_maxLogFiles  = maxLogFiles;
	}

	/**
	 * This method registers a LogDataSource with this ValueLogger.
	 * Values are output from the data sources in the order they were registered.
	 * 
	 * @param src  a LogDataSource that will output log values via this ValueLogger.
	 */
	public void registerDataSource ( LogDataSource src )
	{
		m_sourceList.add ( src );
	}

	/**
	 * This method initializes the ValueLogger for a new phase of operations.
	 * It always closes any open log dump file.
	 * In addition, for the AUTONOMOUS and TELEOP phases, it reads the configuration file
	 * to determine how to display specific values, opens a new log dump file to which this
	 * ValueLogger will write values during the phase, and writes the column headings line
	 * to that log file.
	 * @param whichPhase  the phase being initialized - pass AUTONOMOUS_PHASE, TELEOP_PHASE, or DISABLED_PHASE
	 */
	public void initializePhase ( String whichPhase )
	{
		closeLogFile();
		if ( whichPhase.equals(AUTONOMOUS_PHASE) || whichPhase.equals(TELEOP_PHASE) )
		{
			readConfigFile();
			openLogFile();
			logValues(true/* do headers */, System.nanoTime());
		}
	}

	/**
	 * This method gathers and logs information, and should be called once in each call to
	 * the Robot's autonomousPeriodic() or teleopPeriodic() methods.
	 * 
	 * @param phaseStartTime  the value of System.nanoTime() at the start of the phase
	 */
	public void logValues(long phaseStartTime)
	{
		logValues ( false/*don't do headers*/, phaseStartTime );
	}

	/**
	 * This method is called by a LogDataSource to log a boolean value.
	 * 
	 * @param name   the name assigned to the value
	 * @param value  the value to be logged
	 * @return       the ValueLogger object, so that calls can be chained.
	 */
	public ValueLogger logBooleanValue ( String name, boolean value )
	{
		return logOneValue ( name, Boolean.toString(value), getDecisions(name) );
	}

	/**
	 * This method is called by a LogDataSource to log a double value.
	 * 
	 * @param name   the name assigned to the value
	 * @param value  the value to be logged
	 * @return       the ValueLogger object, so that calls can be chained.
	 */
	public ValueLogger logDoubleValue ( String name, double value )
	{
		return logOneValue ( name, Double.toString(value), getDecisions(name) );
	}

	/**
	 * This method is called by a LogDataSource to log an int value.
	 * 
	 * @param name   the name assigned to the value
	 * @param value  the value to be logged
	 * @return       the ValueLogger object, so that calls can be chained.
	 */
	public ValueLogger logIntValue ( String name, int value )
	{
		return logOneValue ( name, Integer.toString(value), getDecisions(name));
	}

	/**
	 * This method is called by a LogDataSource to log a String value.
	 * 
	 * @param name   the name assigned to the value
	 * @param value  the value to be logged
	 * @return       the ValueLogger object, so that calls can be chained.
	 */
	public ValueLogger logStringValue ( String name, String value )
	{
		return logOneValue ( name, value, getDecisions(name));
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	// P R I V A T E   M E T H O D S

	private FileWriter         m_logWriter;
	private StringBuilder      m_sbh, m_sbv;
	private Map<String,String> m_config;

	private void openLogFile()
	{
		String filename = null;
		long min_lastmod = -1;
		for ( int i = 0; i < m_maxLogFiles; ++i ) 
		{
		    File f = new File(m_filePathBase+i+".csv");
		    if ( f.exists() == false )
		    {
		        filename = f.getAbsolutePath();
		        break;
		    }
		    else if ( (min_lastmod == -1) || (f.lastModified() < min_lastmod) ) 
		    {
		        filename = f.getAbsolutePath();
		        min_lastmod = f.lastModified();
		    }
		}
		try {
			m_logWriter = new FileWriter(filename, false);
		} catch ( IOException e ) {
			System.out.println(e);
			m_logWriter = null;
		}
	}

	private void closeLogFile()
	{
		try { m_logWriter.close(); } catch ( Exception ex ) {}
		m_logWriter = null;
	}

	private void logValues ( boolean writeColumnHeadings, long phaseStartTime )
	{
		if ( writeColumnHeadings ) {
			m_sbh = new StringBuilder();
			m_sbh.append("Start Nanos,Stop Nanos");
		} else {
			m_sbh = null;
		}
		m_sbv = new StringBuilder();
		m_sbv.append(phaseStartTime).append(",").append(System.nanoTime());
		for ( LogDataSource src : m_sourceList ) {
			src.gatherValues(this);
		}
		if ( m_logWriter != null ) {
			try {
				if ( m_sbh != null ) m_logWriter.write(m_sbh.toString()+"\n");
				m_logWriter.write(m_sbv.toString()+"\n");
			} catch ( IOException e ) {
				System.out.println(e);
				closeLogFile();
			}
		}
	}
	
	private String getDecisions ( String name )
	{
		String cfg = null;
		if ( m_config != null ) m_config.get(name);
		if ( cfg == null ) cfg = "TT";
		return cfg;
	}

	private ValueLogger logOneValue ( String name, String value, String cfg )
	{
		if ( cfg.charAt(0) == 'T' ) {
			// Log it to the dump file
			if ( m_sbh != null ) {
				m_sbh.append(",").append(name);
			}
			m_sbv.append(",").append(value);
		}
		if ( cfg.charAt(1) == 'T' ) {
			SmartDashboard.putString(name+": ",value);
		}
		return this;
	}

	private void readConfigFile()
	{
		m_config = new HashMap<String,String>();
		BufferedReader config_file = null;
		StringBuilder nambuf = new StringBuilder();
		try {
			config_file = new BufferedReader(new FileReader(m_filePathBase+".cfg"));
			String line;
			int line_no = 0;
			while ( (line = config_file.readLine()) != null ) {
				++line_no;
				line = line.trim();
				if ( line.length() == 0 ) continue; // skip blank lines
				if ( line.charAt(0) == '#' ) continue; // skip comments
				String[] tokens = line.split("\\s+");
				if ( tokens.length < 3 ) {
					System.out.println("E R R O R !!"
					  + " In "+m_filePathBase+".cfg, line "+line_no
					  + ": Only found "+tokens.length+" token(s)!"
					);
					continue;
				}
				// The last 2 tokens are the decision.
				// Any others comprise the value name being configured.
				String do_file_token = tokens[tokens.length-2];
				String do_dash_token = tokens[tokens.length-1];
				Boolean do_file = parseAsBoolean(do_file_token);
				if ( do_file == null ) {
					System.out.println("E R R O R !!"
					  + " In "+m_filePathBase+".cfg, line "+line_no
					  + ": The file decision value, \""+do_file_token+"\", makes no sense."
					);
					continue;
				}
				Boolean do_dash = parseAsBoolean(do_dash_token);
				if ( do_dash == null ) {
					System.out.println("E R R O R !!"
					  + " In "+m_filePathBase+".cfg, line "+line_no
					  + ": The dashboard decision value, \""+do_dash_token+"\", makes no sense."
					);
					continue;
				}
				nambuf.setLength(0);
				for ( int itok = 0 ; itok < tokens.length-2 ; ++itok ) {
					nambuf.append(" ").append(tokens[itok]);
				}
				String name = nambuf.substring(1);
				String decisions = (do_file && do_dash) ? "TT" :
					                do_file             ? "TF" :
					                           do_dash  ? "FT" :
					            	                      "FF" ;
				m_config.put(name,decisions);
			}
		} catch ( IOException e ) {
			System.out.println(e);
		} finally {
			if ( config_file != null ) {
				try { config_file.close(); } catch ( Exception ex ) {}
			}
		}
	}

	private Boolean parseAsBoolean ( String x )
	{
		String x_lower = x.toLowerCase();
		if ( x_lower.charAt(0) == 't' ) return new Boolean(true);
		if ( x_lower.charAt(0) == 'f' ) return new Boolean(false);
		if ( x_lower.charAt(0) == 'y' ) return new Boolean(true);
		if ( x_lower.charAt(0) == 'n' ) return new Boolean(false);
		return null;
	}
}
