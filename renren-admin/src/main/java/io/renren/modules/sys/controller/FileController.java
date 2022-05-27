package io.renren.modules.sys.controller;


//import com.sun.media.jfxmedia.logging.Logger;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.FmsDocumentFileEntity;
import io.renren.modules.sys.service.FmsDocumentFileService;
import io.renren.modules.sys.utils.FileNameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("sys/file")
@RestController
public class FileController extends AbstractController {
    private final ResourceLoader resourceLoader;

    @Autowired
    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.recycle-path}")
    private String delpath;

    @Autowired
    public FmsDocumentFileService fmsDocumentFileService;

    @PostMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file,@RequestParam("document") String docID){
        String response = " ";


        if (file.isEmpty()) {
            return "文件为空！";
        }
        try {
            //1.定义上传的文件
            String localPath = path;
            //2.获得文件名字
            String fileName = file.getOriginalFilename();
            //3.上传

            //3.1 生成新的文件名
            String realPath = localPath +  FileNameUtils.getFileName(fileName);
            //3.2 保存文件
            File dest = new File(realPath);
            //判断文件目目录是否存在,不存在则新建
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdir();
            }
            file.transferTo(dest);

            //保存路径到数据库
            // FmsDocumentEntity fmsDocumentEntity = new FmsDocumentEntity();
            FmsDocumentFileEntity fmsDocumentFileEntity=new FmsDocumentFileEntity();
            fmsDocumentFileEntity.setDocId(Long.parseLong(docID));
            fmsDocumentFileEntity.setFileName(fileName);
            fmsDocumentFileEntity.setFileLocation(realPath);
            fmsDocumentFileEntity.setFileSize((float)file.getSize()/1000.0f);

            fmsDocumentFileEntity.setUpload_time(new Date());
            fmsDocumentFileService.save(fmsDocumentFileEntity);
           // fmsDocumentFileService



            response = "上传成功";

        } catch (Exception e) {
            e.printStackTrace();
            response = "服务器出现错误，上传失败";
        }
        return response;
    }
   // @("/export")
   @SysLog("档案浏览")
    @RequestMapping("/fileDownload/{docfileId}")
    @ResponseBody
    public void fileDownload(HttpServletResponse response,@PathVariable("docfileId")Long docfileId,@RequestParam("document") String docID)throws IOException {
        FmsDocumentFileEntity fmsDocumentFileEntity = fmsDocumentFileService.getById(docfileId);

        String realPath = fmsDocumentFileEntity.getFileLocation();
        String fileName = fmsDocumentFileEntity.getFileName();
        FileInputStream fis=null;
        BufferedInputStream bis=null;
        File file  = new File(realPath);
        if(file.exists()){
            try{
                //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
                response.setContentType("application/force-download");
                //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                //进行读写操作
                byte[]buffer=new byte[1024];
                fis=new FileInputStream(file);
                bis=new BufferedInputStream(fis);
                OutputStream os=response.getOutputStream();
                //从源文件中读
                int i=bis.read(buffer);
                while(i!=-1){
                    //写到response的输出流中
                    os.write(buffer,0,i);
                    i=bis.read(buffer);

                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                //善后工作，关闭各种流
                try {
                    if(bis!=null){
                        bis.close();
                    }
                    if(fis!=null){
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       // return R.ok().put("文件",fmsDocumentFileEntity);
    }

    @RequestMapping("/showfile/{docId}")
    public R showfile(@PathVariable("docId") Long docId
    ){
        FmsDocumentFileEntity fmsDocumentFile = fmsDocumentFileService.getById(docId);
        return R.ok().put("所有文件",fmsDocumentFile);


    }
    @RequestMapping("/getuploadPath")
    public R getuploadPath(
    ){
        return R.ok().put("uploadpath",path);


    }
    @RequestMapping("/deleteSingle")
    public R deleteSingle(@RequestParam("docfileId") String docfileId,@Param("userId") String userId){
        FmsDocumentFileEntity fmsDocumentFileEntity = fmsDocumentFileService.getById(Long.parseLong(docfileId));
        String srcPath = fmsDocumentFileEntity.getFileLocation();
        String destPath = delpath;
        //String fileName = fmsDocumentFileEntity.getFileName();
        //String desPath= destPath  + fileName;

        File srcFile=new File(srcPath);
        if(!srcFile.exists()){
            return R.ok("源文件不存在");
        }
        String fileName = srcFile.getName();
        File file = new File(destPath,fileName);

//        //判断文件目录是否存在，没有则新建
//        if(!file.getParentFile().exists()){
//            file.getParentFile().mkdir();
//        }
        copyFile(srcFile, file);
        if (srcFile.delete()) {
            System.out.println("删除文件" + fileName + "成功！");
        } else {
            System.out.println("删除文件" + fileName + "失败！");
        }
        fmsDocumentFileService.deldocfile(Long.parseLong(docfileId),Long.parseLong(userId));
        return R.ok("文件删除成功！");
    }
    private  void copyFile(File srcPath, File destPath){

        try{
            //2.创建输入输出流对象
            BufferedInputStream fis =new BufferedInputStream( new FileInputStream(srcPath));
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(destPath));

            //创建搬运工具
            byte datas[] = new byte[1024*8];
            //创建长度
            int len = 0;
            //循环读取数据
            while((len = fis.read(datas))!=-1){
                fos.write(datas,0,len);
            }
            //3.释放资源
            fis.close();
            fos.close();
        }catch (Exception e){
            logger.error("copy error"+srcPath.getAbsolutePath()+"/"+destPath.getAbsolutePath(),e);
        }
    }


//    @PostMapping("/fileUploads")
//    @ResponseBody
//    public String fileUploads(@RequestParam("file") MultipartFile[] files){
//        String response = " ";
//        if (files == null || files.length < 1) {
//            return "文件不能为空";
//        }
//
//        try{
//            for (int i = 0; i < files.length; i++){
//                MultipartFile file = files[i];
//                String localPath = path;
//                String fileName = file.getOriginalFilename();
//                String realPath = localPath + "/" + FileNameUtils.getFileName(fileName);
//                File dest = new File(realPath);
//                if (!dest.getParentFile().exists()){
//                    dest.getParentFile().mkdir();
//                }
//                file.transferTo(dest);
//                FmsDocumentEntity fmsDocumentEntity = new FmsDocumentEntity();
//                fmsDocumentEntity.setStorePlace(realPath);
//                fmsDocumentEntity.setCreateTime(new Date());
//                fmsDocumentFileService.save(fmsDocumentEntity);
//
//                response = "上传成功";
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            response = "服务器出现错误，上传失败";
//        }
//        return response;
//    }

}

