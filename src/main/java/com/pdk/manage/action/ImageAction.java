package com.pdk.manage.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdk.manage.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/9/3.
 */
@Controller
@RequestMapping("/img")
public class ImageAction {
    private static Logger log = LoggerFactory.getLogger(ImageAction.class);

    @RequestMapping(value="/img_upload/{employeeId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> headerImgupload(@RequestParam(value="imageFile", required = false) MultipartFile file, @PathVariable String employeeId) {
        Map<String, Object> result = new HashMap<>();

        String orgnFileName = file.getOriginalFilename();
        String ext = orgnFileName.substring(orgnFileName.lastIndexOf("."));
        String fileName = UUIDGenerator.generateUUID() + ext;

        String path = CommonUtil.getRealPath(CommonConst.HEADER_IMAGE_PATH);

        File folder = new File(path);
        File targetFile = new File(path, fileName);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }

        try {
            file.transferTo(targetFile);
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            result.put("errMsg", e.getMessage());
            log.error(e.getMessage(), e);
        }

        result.put("headerImage", targetFile.getName());
        return result;
    }

    @RequestMapping(value="/img_cut", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> imgCut(String fileName, int x, int y, int w, int h) {
        Map<String, Object> result = new HashMap<>();

        String path = CommonUtil.getRealPath(CommonConst.HEADER_IMAGE_PATH);

        try {
            URL imageRUL = new URL(CommonUtil.getResourcePath() + "/" + fileName);

            try {
                File folder = new File(path);
                if ( !folder.getParentFile().exists()) {
                    folder.mkdirs();
                }
                String ext = fileName.substring(fileName.lastIndexOf("."));
                File cutFile = new File(path, UUIDGenerator.generateUUID() + ext);

                ImageCutHelper cut = new ImageCutHelper();
                cut.cutImage(imageRUL.openStream(), x, y, w, h, cutFile);

                String fileUri = uploadImageFile(IdGenerator.SM_MODULE_CODE, "/head_img/employee_img", cutFile);

                result.put("result", "success");
                result.put("headerImage", fileUri);

                cutFile.delete();
            } catch (Exception e) {
                result.put("result", "error");
                result.put("errMsg", "图片格式有误，无法裁剪，请更换图片！");
                log.error(e.getMessage(), e);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value="/goodsUpload" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadGoodsImg(@RequestParam(value="imageFile", required = false) MultipartFile file){
        Map<String, Object> result = new HashMap<>();
        String orgnFileName = file.getOriginalFilename();
        String ext = orgnFileName.substring(orgnFileName.lastIndexOf("."));
        String fileName = UUIDGenerator.generateUUID() + ext;

        String path = CommonUtil.getRealPath("static/head_img/goods_img");

        File folder = new File(path);
        File targetFile = new File(path, fileName);
        if ( !folder.exists() ) {
            folder.mkdirs();
        }

        try {
            file.transferTo(targetFile);
            result.put("name",targetFile.getName());
            System.out.println(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }


    public String uploadImageFile(String module, String path, File uploadFile) throws IOException {

        HttpPost post = new HttpPost(CommonUtil.getResourceFileUploadPath());

        CloseableHttpClient httpClient = HttpClients.createDefault();

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody("token", CommonUtil.getResourceToken());
        builder.addTextBody("uploadType", "image");
        builder.addTextBody("module", module);
        builder.addTextBody("path", path);
        builder.addBinaryBody("uploadFile", uploadFile, ContentType.MULTIPART_FORM_DATA, uploadFile.getName());

        post.setEntity(builder.build());

        HttpResponse response = httpClient.execute(post);

        if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                JSONObject uploadJson = JSON.parseObject(EntityUtils.toString(entity, "UTF-8"));
                if(uploadJson.getBoolean("result")) {
                    return  uploadJson.getString("fileUri");
                }
            }
        }
        return null;
    }



}
