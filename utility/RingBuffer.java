package utility;

public class RingBuffer {
	private int[] m_ringBuffer;
	private int m_currentPosition;
	private int m_goodValueCount;
	
	public RingBuffer()
	{
		m_ringBuffer = new int[5];
		m_currentPosition = 0;
		m_goodValueCount = 0;
	}
	
	public void clear()
	{
		m_goodValueCount = 0;
	}
	
	public boolean isFull()
	{
		if (m_goodValueCount >= 5)
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	public int getAverage()
	{
		int min = m_ringBuffer[0];
		int max = m_ringBuffer[0];
		int sum = m_ringBuffer[0];
		
		for(int i = 1; i < m_ringBuffer.length; i++)
		{
			sum += m_ringBuffer[i];
			if(m_ringBuffer[i] > max)
			{
				max = m_ringBuffer[i];
			}
			else if(m_ringBuffer[i] < min)
			{
				min = m_ringBuffer[i];
			}
		}
		return (sum - min) - max;
	}
	
	public void insertDelta(int delta)
	{
		m_ringBuffer[m_currentPosition] = delta;
		m_currentPosition = (m_currentPosition + 1) % 5;
	}
}