package cn.demo.test;

import cn.edustar.jitar.util.ImgUtil;

public class CropImageTest {
    public static void main(String[] args) throws Exception {
        String inFilePath = "d:\\20130814091029404777.jpg";
        String outFilePath = "d:\\20130814091029404777___1.jpg";
        int width = 200, hight = 240;
        ImgUtil.saveImageAsJpg(inFilePath, outFilePath, width, hight);
    }
}
