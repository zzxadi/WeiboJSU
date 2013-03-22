package com.jsu.check;

public class StringChecked
{
	/**
	 * 判断给定字符串是否空白串。 
	 * 空白串是指由空格、制表符(\t)、回车符(\r)、换行符(\n)组成的字符串 
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isBlank(String input)
	{
		if (input == null || "".equals(input))
		{
			return true;
		}

		for (int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
			{
				return false;
			}
		}
		return true;
	}
}