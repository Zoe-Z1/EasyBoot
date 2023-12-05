package cn.easy.boot.common.generator;

/**
 * @author zoe
 * @date 2023/9/24
 * @description 代码生成工具类
 */
public class GenerateUtil {

    /**
     * 判断是否能解析成数据字典域编码
     * @param remarks 列备注
     * @return
     */
    public static boolean isParseDomainCode(String remarks) {
        // 解析注释，仅支持 #A：xxx，B：xxx 格式
        int index = remarks.indexOf("#");
        if (index < 0) {
            return false;
        }
        String dictStr = remarks.substring(index + 1);
        dictStr = dictStr.replaceAll("，", ",");
        dictStr = dictStr.replaceAll("：", ":");
        String[] dictArray = dictStr.split(",");
        int parseCount = 0;
        for (String dict : dictArray) {
            String[] array = dict.split(":");
            if (array.length == 2) {
                parseCount++;
            }
        }
        return parseCount > 0;
    }
}
