using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace ThApiKit
{
    class Program
    {
        static void Main(string[] args)
        {
            String APP_ID = ""; //这里换成你的app_id
            String APP_SECRET = ""; //这里换成你的app_secret

            Dictionary<String, String> param = new Dictionary<String, String>();
            ThApiKit kit = new ThApiKit(APP_ID, APP_SECRET);
            String method = "tuhao.data.balance";
            Console.WriteLine(kit.get(method, param));

            param.Clear();
            // Generate post objects
            param.Add("amount", "10"); // 流量包大小
            param.Add("area", "0"); // 全国或是省份 0 表示全国流量 1 表示省流量
            param.Add("mobile", ""); // 手机号码
            param.Add("effecttime", "0"); // 生效时间 0 表示即时生效 1
            param.Add("roam", "1"); // 是否是漫游流量 1 表示漫游 0 表示不漫游
            method = "tuhao.data.charge";
            Console.WriteLine(kit.post(method, param));
            Console.Read();
        }
    }
}
