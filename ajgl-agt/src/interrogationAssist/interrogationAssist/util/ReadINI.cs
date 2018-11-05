using System;
using System.Data;
using System.Configuration;
using System.IO;
using System.Text;
using System.Collections.Generic;

/// <summary>
/// ReadINI 的摘要说明
/// </summary>
public class ReadINI
{
    private string filePath;
    public string filepath
    {
        get
        {
            return filePath;
        }
        set
        {
            filePath = value;
        }
    }
	public ReadINI()
	{
		//
		// TODO: 在此处添加构造函数逻辑
		//
	}

    public ReadINI(string FilePath)
    {
        filePath = FilePath;
    }

    /// <summary>
    /// 格式化传来的Section值，使其符合标准格式
    /// </summary>
    /// <param name="section">节点值</param>
    /// <returns></returns>
    private string FormatSection(string section)
    {
        if (!(section.StartsWith("[") && section.EndsWith("]")))
        {
            section = "[" + section.Replace("[", "").Replace("]", "") + "]";
        }
        return section;
    }

    private string CheckSection(string strReturn)
    {
        if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf(']') > strReturn.IndexOf('['))
        {
            if (strReturn.IndexOf("//") > -1 || strReturn.IndexOf(";") > -1)
            {
                strReturn = strReturn.Substring(0, strReturn.IndexOf("//") > -1 ? strReturn.IndexOf("//") : strReturn.IndexOf(";"));
            }
            strReturn = strReturn.Trim();
        }

        return strReturn;
    }

    private String GetArrStrResult(string sourceStr,string[] arrResult,int stratIndex,string allAddMark) 
    {
        try
        {
            StringBuilder sb = new StringBuilder();

            for (int i = stratIndex; i < arrResult.Length; i++)
            {
                sb.Append(allAddMark);
                sb.Append(arrResult[i].ToString().Trim());
            }
            return string.Format("{0}{1}", sourceStr, sb.ToString());
        }
        catch (Exception e)
        {
            return sourceStr;
        }
    }

    /// <summary>
    /// 获取Section内关键字为key的值
    /// </summary>
    /// <param name="section">节点值</param>
    /// <param name="key">Key值</param>
    /// <returns></returns>

    public string GetValue(string section, string key)
    {
        string strReturn = "";
        section = FormatSection(section);
        int flag = 0;
        if (File.Exists(filePath))
        {
            using (StreamReader sr = new StreamReader(filePath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    strReturn = CheckSection(strReturn);
                    if (strReturn == section)
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                if (strReturn.IndexOf('=') > 0 && !strReturn.StartsWith(";") && !strReturn.StartsWith("//"))
                                {
                                    string[] arrResult = strReturn.Split('=');
                                    if (arrResult[0].Trim() == key.Trim())
                                    {
                                        strReturn = GetArrStrResult(arrResult[1].ToString().Trim(), arrResult, 2, "=");
                                      //  strReturn = arrResult[1].ToString().Trim();
                                        flag = 1;
                                        break;
                                    }
                                }
                            }
                        }
                        //if (flag == 1)
                        break;
                    }
                }

                if (flag == 1)
                    return strReturn;
                else
                    return "";
            }
        }
        else
        {
            //string path = filePath.Substring(0, filePath.LastIndexOf('/'));
            //if (!Directory.Exists(path))
            //{
            //    Directory.CreateDirectory(path);
            //}
            //File.Create(filePath);
           // Log.WriteFile("文件" + filePath + "不存在，无法进行相关读取操作。", "Err");
            return strReturn;
        }
    }

    /// <summary>
    /// 获取包括等号之前部分的Section整段值
    /// </summary>
    /// <param name="section">节点值</param>
    /// <returns></returns>
    public string[] GetValues(string section)
    {
        string strReturn = "";
        section = FormatSection(section);

        List<string> list = new List<string>();
        if (File.Exists(filePath))
        {
            using (StreamReader sr = new StreamReader(filePath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    strReturn = CheckSection(strReturn);

                    if (strReturn == section)
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                //if (strReturn.IndexOf('=') > 0 && !strReturn.StartsWith("//"))
                                if (!strReturn.StartsWith("//") && !strReturn.StartsWith(";") && strReturn != "")
                                {
                                    list.Add(strReturn);
                                }
                            }
                        }
                        break;
                    }
                }

                return list.ToArray();
            }
        }
        else
        {
            return null;
        }
    }

    /// <summary>
    /// 获取包括等号之前部分的Section中的关键字为Key的集合
    /// </summary>
    /// <param name="section">节点值</param>
    /// <param name="key">Key值</param>
    /// <returns></returns>
    public string[] GetValues(string section, string key)
    {
        string strReturn = "";
        section = FormatSection(section);

        List<string> list = new List<string>();
        if (File.Exists(filePath))
        {
            using (StreamReader sr = new StreamReader(filePath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    strReturn = CheckSection(strReturn);

                    if (strReturn == section)
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                //if (strReturn.IndexOf('=') > 0 && !strReturn.StartsWith("//"))
                                if (!strReturn.StartsWith("//") && !strReturn.StartsWith(";") && strReturn != "")
                                {
                                    string[] arrResult = strReturn.Split('=');
                                    if (arrResult[0] == key)
                                    {
                                        list.Add(strReturn);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }

                return list.ToArray();
            }
        }
        else
        {
            return null;
        }
    }

    /// <summary>
    /// 获取不包括等号之前部分的Section整段值
    /// </summary>
    /// <param name="section">节点值</param>
    /// <returns>返回此节点所有值的数组</returns>
    public string[] GetKeyValues(string section)
    {
        string strReturn = "";
        section = FormatSection(section);

        List<string> list = new List<string>();
        if (File.Exists(filePath))
        {
            using (StreamReader sr = new StreamReader(filePath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    strReturn = CheckSection(strReturn);
                    if (strReturn == section)
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                //if (strReturn.IndexOf('=') > 0 && !strReturn.StartsWith("//"))
                                if (!strReturn.StartsWith("//") && !strReturn.StartsWith(";") && strReturn != "")
                                {
                                    if (strReturn.IndexOf('=') > -1)
                                    {
                                        strReturn = strReturn.Split('=')[1].ToString();
                                    }
                                    list.Add(strReturn);
                                }
                            }
                        }
                        break;
                    }
                }

                return list.ToArray();
            }
        }
        else
        {
            return null;
        }
    }

    /// <summary>
    /// 获取不包括等号之前部分的Section中关键字为Key的值的集合
    /// </summary>
    /// <param name="section">节点值</param>
    /// <param name="key">Key值</param>
    /// <returns></returns>
    public string[] GetKeyValues(string section,string key)
    {
        string strReturn = "";
        section = FormatSection(section);

        List<string> list = new List<string>();
        if (File.Exists(filePath))
        {
            using (StreamReader sr = new StreamReader(filePath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    strReturn = CheckSection(strReturn);
                    if (strReturn == section)
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                //if (strReturn.IndexOf('=') > 0 && !strReturn.StartsWith("//"))
                                if (!strReturn.StartsWith("//") && !strReturn.StartsWith(";") && strReturn != "")
                                {
                                    if (strReturn.IndexOf('=') > -1)
                                    {
                                        string[] arrResult = strReturn.Split('=');
                                        if (arrResult[0] == key)
                                        {
                                            strReturn = strReturn.Split('=')[1].ToString();
                                            list.Add(strReturn);
                                        }                                        
                                    }
                                }
                            }
                        }
                        break;
                    }
                }

                return list.ToArray();
            }
        }
        else
        {
            return null;
        }
    }

    /// <summary>
    /// 获取模糊匹配的值，返回数组
    /// </summary>
    /// <param name="section">节点名</param>
    /// <param name="key">Key值</param>
    /// <returns></returns>
    public string[] GetBlurMatch(string section, string key)
    {
        string strReturn = "";
        section = FormatSection(section);
        List<string> list = new List<string>();
        if (File.Exists(filepath))
        {
            using (StreamReader sr = new StreamReader(filepath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    if (strReturn.StartsWith(section))
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                if (strReturn.StartsWith(key))
                                {
                                    if (strReturn.IndexOf('=') > -1)
                                    {
                                        strReturn = strReturn.Split('=')[1].ToString();
                                        list.Add(strReturn);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                return list.ToArray();
            }
        }
        else
        {
            return null;
        }
    }

    /// <summary>
    /// 在Section范围内的第一行之前添加一行
    /// </summary>
    /// <param name="section">要添加行的节点名</param>
    /// <param name="key">要添加的Key值</param>
    /// <param name="value">要添加的Value值</param>
    public void AddFirst(string section, string key, string value)
    {
        if (File.Exists(filePath))
        {
            bool hasElement = false;
            string strReadLine = "";
            string strContent = "";
            section = FormatSection(section);

            StreamReader sr = new StreamReader(filePath, Encoding.Default);

            while ((strReadLine = sr.ReadLine()) != null)
            {
                strContent += strReadLine + "\r\n";
                strReadLine = CheckSection(strReadLine);

                if (strReadLine == section)
                {
                    if (key == "" && value == "")
                    {
                        strContent += "\r\n";
                    }
                    else
                    {
                        strContent += key + "=" + value + "\r\n";
                    }
                    hasElement = true;
                }
            }
            if (hasElement == false)
            {
                strContent += section + "\r\n" + key + "=" + value + "\r\n";
            }
            sr.Close();

            strContent = strContent.Substring(0, strContent.Length - 2);
            StreamWriter sw = new StreamWriter(filePath, false, Encoding.Default);
            sw.WriteLine(strContent);
            sw.Close();
        }
    }

    /// <summary>
    /// 在Section范围内的最后一行之后添加一行
    /// </summary>
    /// <param name="section">要添加行的节点名</param>
    /// <param name="key">要添加的Key值</param>
    /// <param name="value">要添加的Value值</param>
    public void AddLast(string section, string key, string value)
    {
        if (File.Exists(filePath))
        {
            section = FormatSection(section);
            bool hasElement = false;
            bool start = false;
            bool addOK = false;
            string strReadLine = "";
            string strContent = "";
            string strNULL = "";

            StreamReader sr = new StreamReader(filePath, Encoding.Default);

            while ((strReadLine = sr.ReadLine()) != null)
            {
                if (start == true)
                {
                    if (strReadLine == "")
                    {
                        strNULL += "\r\n";
                    }
                    else if (strReadLine.IndexOf('[') > -1 && strReadLine.IndexOf('[') < strReadLine.IndexOf(']'))
                    {
                        if (key == "" && value == "")
                        {
                            strContent += "\r\n" + strNULL;
                        }
                        else
                        {
                            strContent += key + "=" + value + "\r\n" + strNULL;
                        }
                        addOK = true;
                        strNULL = "";
                        start = false;
                    }
                    else
                    {
                        strContent += strNULL;
                        strNULL = "";
                    }
                }

                string strReadLineCopy = CheckSection(strReadLine);
                if (strReadLineCopy == section)
                {
                    hasElement = true;
                    start = true;
                }
                if (!(start == true && strReadLine == ""))
                {
                    strContent += strReadLine + "\r\n";
                }
            }
            if (hasElement == false)
            {
                strContent += section + "\r\n" + key + "=" + value + "\r\n";
            }
            if (hasElement == true && addOK == false)
            {
                strContent += key + "=" + value + "\r\n";
            }

            sr.Close();

            strContent = strContent.Substring(0, strContent.Length - 2);
            StreamWriter sw = new StreamWriter(filePath, false, Encoding.Default);
            sw.WriteLine(strContent);
            sw.Close();
        }
    }

    /// <summary>
    /// 编辑选定行的值，但不可编辑关键字的值
    /// </summary>
    /// <param name="section">要编辑行的节点值</param>
    /// <param name="key">要编辑行的Key值</param>
    /// <param name="value">要编辑行的新Value值</param>
    public void Edit(string section, string key, string value)
    {
        if (File.Exists(filePath))
        {
            section = FormatSection(section);
            string strReadLine = "";
            string strContent = "";
            bool flag = false;

            StreamReader sr = new StreamReader(filePath, Encoding.Default);
            while ((strReadLine = sr.ReadLine()) != null)
            {
                strContent += strReadLine + "\r\n";
                strReadLine = CheckSection(strReadLine);
                if (strReadLine == section)
                {
                    while ((strReadLine = sr.ReadLine()) != null)
                    {
                        string strStaValue = "";
                        string strEndValue = "";
                        if (strReadLine.IndexOf('=') > -1)
                        {
                            strEndValue = strReadLine.Substring(strReadLine.IndexOf('='));
                            strStaValue = strReadLine.Substring(0, strReadLine.IndexOf('='));
                        }

                        if (strStaValue.Trim() == key.Trim())
                        {
                            flag = true;
                            if (value == "")
                            {
                                strContent += value;
                            }
                            else
                            {
                                strContent += key + "=" + value + "\r\n";
                            }
                        }
                        else
                        {
                            strContent += strReadLine + "\r\n";
                        }
                    }
                }
            }

            sr.Close();
            strContent = strContent.Substring(0, strContent.Length - 2);
            StreamWriter sw = new StreamWriter(filePath, false, Encoding.Default);
            sw.WriteLine(strContent);
            sw.Close();

            if (flag == false && value != "")
            {
                AddLast(section, key, value);
            }
        }
    }

    /// <summary>
    /// 编辑整行的值，可以转变关键字
    /// </summary>
    /// <param name="section">要编辑行的节点名</param>
    /// <param name="oldkey">要编辑行的原Key值</param>
    /// <param name="newkey">要编辑行的新Key值</param>
    /// <param name="value">要编辑行的新Value值</param>
    public void EditLine(string section, string oldkey, string newkey, string value)
    {
        if (File.Exists(filePath))
        {
            string strReadLine = "";
            string strContent = "";
            bool flag = false;
            section = FormatSection(section);

            StreamReader sr = new StreamReader(filePath, Encoding.Default);
            while ((strReadLine = sr.ReadLine()) != null)
            {
                strContent += strReadLine + "\r\n";
                strReadLine = CheckSection(strReadLine);

                if (strReadLine == section)
                {
                    while ((strReadLine = sr.ReadLine()) != null)
                    {
                        string strStaValue = "";
                        string strEndValue = "";
                        if (strReadLine.IndexOf('=') > -1)
                        {
                            strEndValue = strReadLine.Substring(strReadLine.IndexOf('='));
                            strStaValue = strReadLine.Substring(0, strReadLine.IndexOf('='));
                        }

                        if (strStaValue == oldkey)
                        {
                            flag = true;
                            if (value == "")
                            {
                                strContent += value;
                            }
                            else
                            {
                                strContent += newkey + "=" + value + "\r\n";
                            }
                        }
                        else
                        {
                            strContent += strReadLine + "\r\n";
                        }
                    }
                }
            }

            sr.Close();
            strContent = strContent.Substring(0, strContent.Length - 2);
            StreamWriter sw = new StreamWriter(filePath, false, Encoding.Default);
            sw.WriteLine(strContent);
            sw.Close();

            if (flag == false && value != "")
            {
                AddLast(section, newkey, value);
            }
        }
    }

    /// <summary>
    /// 根据关键字的值，删除整行
    /// </summary>
    /// <param name="section">节点名</param>
    /// <param name="key">要删除行的Key值</param>
    public void DelBaseKey(string section, string key)
    {
        Edit(section, key, "");
    }

    /// <summary>
    /// 根据value的值，删除整行，需提供Key值
    /// </summary>
    /// <param name="section">节点名</param>
    /// <param name="key">要删除行的key值</param>
    /// <param name="value">要删除行的Value值</param>
    public void DelBaseValue(string section, string key, string value)
    {
        if (File.Exists(filePath))
        {
            section = FormatSection(section);
            string strReadLine = "";
            string strContent = "";

            StreamReader sr = new StreamReader(filePath, Encoding.Default);
            while ((strReadLine = sr.ReadLine()) != null)
            {
                strContent += strReadLine + "\r\n";
                strReadLine = CheckSection(strReadLine);

                if (strReadLine == section)
                {
                    while ((strReadLine = sr.ReadLine()) != null)
                    {
                        string strStaValue = "";
                        string strEndValue = "";
                        if (strReadLine.IndexOf('=') > -1)
                        {
                            strEndValue = strReadLine.Substring(strReadLine.IndexOf('=') + 1).Trim();
                            strStaValue = strReadLine.Substring(0, strReadLine.IndexOf('=')).Trim();
                        }

                        if (strStaValue == key)
                        {
                            //string[] arrLine = strReadLine.Split('=');
                            if (strEndValue != value)
                            {
                                strContent += strReadLine + "\r\n";
                            }
                        }
                        else
                        {
                            strContent += strReadLine + "\r\n";
                        }
                    }
                }
            }

            sr.Close();
            strContent = strContent.Substring(0, strContent.Length - 2);
            StreamWriter sw = new StreamWriter(filePath, false, Encoding.Default);
            sw.WriteLine(strContent);
            sw.Close();
        }
    }

    /// <summary>
    /// 根据value的值，删除整行，不需提供Key值
    /// </summary>
    /// <param name="section">节点名</param>
    /// <param name="value">要删除行的Value值</param>
    public void DelBaseValue(string section, string value)
    {
        if (File.Exists(filePath))
        {
            section = FormatSection(section);
            string strReadLine = "";
            string strContent = "";

            StreamReader sr = new StreamReader(filePath, Encoding.Default);
            while ((strReadLine = sr.ReadLine()) != null)
            {
                strContent += strReadLine + "\r\n";
                strReadLine = CheckSection(strReadLine);

                if (strReadLine == section)
                {
                    while ((strReadLine = sr.ReadLine()) != null)
                    {
                        string strStaValue = "";
                        string strEndValue = "";
                        if (strReadLine.IndexOf('=') > -1)
                        {
                            strEndValue = strReadLine.Substring(strReadLine.IndexOf('=') + 1).Trim();
                            strStaValue = strReadLine.Substring(0, strReadLine.IndexOf('=')).Trim();
                        }

                        if (strEndValue != value)
                        {
                            strContent += strReadLine + "\r\n";
                        }
                    }
                }
            }

            sr.Close();
            strContent = strContent.Substring(0, strContent.Length - 2);
            StreamWriter sw = new StreamWriter(filePath, false, Encoding.Default);
            sw.WriteLine(strContent);
            sw.Close();
        }
    }

    /// <summary>
    /// 将所有的信息写入文本文件中
    /// </summary>
    /// <param name="strContent">要写入的内容</param>
    public void WriteToIni(string strContent)
    {
        StreamWriter sw = new StreamWriter(filePath, false, Encoding.Default);
        sw.WriteLine(strContent);
        sw.Close();
    }

    /// <summary>
    /// 读取文本文件中的所有内容
    /// </summary>
    /// <returns></returns>
    public string ReadFromIni()
    {
        if (File.Exists(filePath))
        {
            string strReturn = "";
            string strReadLine = "";

            StreamReader sr = new StreamReader(filePath, Encoding.Default);
            while ((strReadLine = sr.ReadLine()) != null)
            {
                strReturn += strReadLine + Environment.NewLine;
            }
            sr.Close();
            sr.Dispose();

            return strReturn;
        }
        else
        {
            return "";
        }
    }



    public Dictionary<string, string> GetSectionAllValue(string section)
    {
        string strReturn = "";
        section = FormatSection(section);
        int flag = 0;
        Dictionary<string, string> tampDic = new Dictionary<string, string>();
        if (File.Exists(filePath))
        {
            using (StreamReader sr = new StreamReader(filePath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    strReturn = CheckSection(strReturn);
                    if (strReturn == section)
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                if (strReturn.IndexOf('=') > 0 && !strReturn.StartsWith(";") && !strReturn.StartsWith("//"))
                                {
                                    string[] arrResult = strReturn.Split('=');
                                    string tempStr = GetArrStrResult(arrResult[1].ToString().Trim(), arrResult, 2, "=");
                                    tampDic.Add(arrResult[0].Trim(), tempStr);
                                   // tampDic.Add(arrResult[0].Trim(), arrResult[1].ToString().Trim());
                                   // if (arrResult[0].Trim() == key.Trim())
                                   // {
                                    //    strReturn = arrResult[1].ToString().Trim();
                                    ////    flag = 1;
                                     //   break;
                                   // }
                                }
                            }
                        }
                        //if (flag == 1)
                        break;
                    }
                }

                if (flag == 1)
                    return tampDic;
                else
                    return tampDic;
            }
        }
        else
        {
            //string path = filePath.Substring(0, filePath.LastIndexOf('/'));
            //if (!Directory.Exists(path))
            //{
            //    Directory.CreateDirectory(path);
            //}
            //File.Create(filePath);
            // Log.WriteFile("文件" + filePath + "不存在，无法进行相关读取操作。", "Err");
            return tampDic;
        }
    }

    public List<string> GetSectionAll(string section)
    {
        string strReturn = "";
        section = FormatSection(section);
        int flag = 0;
        List<string> tampList = new List<string>();
        if (File.Exists(filePath))
        {
            using (StreamReader sr = new StreamReader(filePath, Encoding.Default))
            {
                while ((strReturn = sr.ReadLine()) != null)
                {
                    strReturn = CheckSection(strReturn);
                    if (strReturn == section)
                    {
                        while ((strReturn = sr.ReadLine()) != null)
                        {
                            if (strReturn.IndexOf('[') > -1 && strReturn.IndexOf('[') < strReturn.IndexOf(']'))
                            {
                                break;
                            }
                            else
                            {
                                if (strReturn.IndexOf('=') > 0 && !strReturn.StartsWith(";") && !strReturn.StartsWith("//"))
                                {
                                    //string[] arrResult = strReturn.Split('=');
                                    tampList.Add(strReturn);
                                    // if (arrResult[0].Trim() == key.Trim())
                                    // {
                                    //    strReturn = arrResult[1].ToString().Trim();
                                    ////    flag = 1;
                                    //   break;
                                    // }
                                }
                            }
                        }
                        //if (flag == 1)
                        break;
                    }
                }

                if (flag == 1)
                    return tampList;
                else
                    return tampList;
            }
        }
        else
        {
            //string path = filePath.Substring(0, filePath.LastIndexOf('/'));
            //if (!Directory.Exists(path))
            //{
            //    Directory.CreateDirectory(path);
            //}
            //File.Create(filePath);
            // Log.WriteFile("文件" + filePath + "不存在，无法进行相关读取操作。", "Err");
            return tampList;
        }
    }
}
