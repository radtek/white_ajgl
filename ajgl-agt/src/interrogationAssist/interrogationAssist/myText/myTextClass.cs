using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace interrogationAssist.myText
{
    public class myTextClass
    {
        int index = 0;

        public int Index
        {
            get { return index; }
            set { index = value; }
        }
        string id = "";

        public string Id
        {
            get { return id; }
            set { id = value; }
        }
        string text = "";

        public string Text
        {
            get { return text; }
            set { text = value; }
        }
        string remark = "";

        public string Remark
        {
            get { return remark; }
            set { remark = value; }
        }
    }
}
