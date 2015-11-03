package com.pdk.manage.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by hubo on 2015/8/11
 */
@Controller
public class ValidateCodeImageAction {

    private static Logger log = LoggerFactory.getLogger(ValidateCodeImageAction.class);

    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";
    private final Random random = new Random();
    private final static String randString = "0123456789";//abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int width = 108;//图片宽度
    private final int height = 44;//图片高度
    //private int lineSize = 40;//线条个数
    private int stringNum = 4;//个数
    /**
     * 获取字体
     */
    private Font getFont(){
        return new Font("Fixedsys",Font.BOLD, 24);
    }
    /**
     * 获取随机颜色
     */
    private Color getRandColor(int fc,int bc){
        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
        return new Color(r,g,b);
    }

    @RequestMapping("/img/validate_code")
    public void getRandCode(HttpServletRequest request,
                            HttpServletResponse response) {

        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);

        HttpSession session = request.getSession();
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(110, 133));
        //画线条
//      for(int i=0;i<=lineSize;i++){
//          drowLine(g);
//      }

        String randomString = "";
        for(int i=1;i<=stringNum;i++){
            randomString=drowString(g,randomString,i);
        }
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);

        g.dispose();

        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());//½«ÄÚ´æÖÐµÄÍ¼Æ¬Í¨¹ýÁ÷¶¯ÐÎÊ½Êä³öµ½¿Í»§¶Ë
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
    /**
     * 画字符
     */
    private String drowString(Graphics g,String randomString,int i){
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 14*i, 30);
        return randomString;
    }
    /**
     * 画随机线条，防止破解图片内容
     */
//    private void drowLine(Graphics g){
//        int x = random.nextInt(width);
//        int y = random.nextInt(height);
//        int xl = random.nextInt(13);
//        int yl = random.nextInt(15);
//        g.drawLine(x, y, x+xl, y+yl);
//    }

    /**
     * 获取随机字符
     */
    public String getRandomString(int num){
        return String.valueOf(randString.charAt(num));
    }


}
