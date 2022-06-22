package com.example.marketback.controller.boss;

import com.example.marketback.entity.boss.Boss;
import com.example.marketback.entity.boss.BossImage;
import com.example.marketback.request.BossMarketInfoRequest;
import com.example.marketback.response.BossBackProfileImg;
import com.example.marketback.service.member.BossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/boss")
public class BossController {

    @Autowired
    private BossService bossService;

    @PostMapping("/checkMember")
    public boolean checkMember(@RequestBody Map<String, String> map){
        System.out.println(map.get("id"));

        return bossService.checkBossMember(map.get("id"));
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> bossRegister(@RequestParam("imgFile") MultipartFile imgFile,
                                       @RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam("region") String region,
                                       @RequestParam("category") String category){

        log.info("bossRegister");
        log.info("imgFile" + imgFile);

        try{
            log.info("requestUploadFile() - Make file: " + imgFile.getOriginalFilename());

            FileOutputStream file = new FileOutputStream("../marketfront/src/assets/bossProfile/" + id + "_" + imgFile.getOriginalFilename());

            String fileSrc = id + "_" + imgFile.getOriginalFilename();

            System.out.println(fileSrc+", "+name+", "+region);
            file.write(imgFile.getBytes());
            file.close();

            bossService.register(fileSrc, id, name, region, category);
        } catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/pageView")
    public Boss bossView(@RequestBody Map<String, Long> memberNo){
        log.info("bossView");
        log.info(memberNo.get("memberNo")+"");

        return bossService.getPage(memberNo.get("memberNo"));
    }

    @PostMapping("/saveMarketInfo")
    public void saveMarketInfo (@RequestBody BossMarketInfoRequest bossMarketInfoRequest){
        log.info("marketInfo" + bossMarketInfoRequest.getId());

        bossService.saveMarketInfo(bossMarketInfoRequest);
    }

    @PostMapping("/modifyProfile")
    public ResponseEntity<Boolean> modifyProfile (@RequestParam("imgFile") MultipartFile imgFile,
                                                @RequestParam("id") String id){

        try{
            log.info("requestUploadFile() - Make file: " + imgFile.getOriginalFilename());

            FileOutputStream file = new FileOutputStream("../marketfront/src/assets/bossProfile/front/" + id + "_" + imgFile.getOriginalFilename());

            String fileSrc = id + "_" + imgFile.getOriginalFilename();

            file.write(imgFile.getBytes());
            file.close();

            bossService.modifyProfile(fileSrc, id);
        } catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/saveBackImg")
    public ResponseEntity<Boolean> saveBackProfile (@RequestParam("fileList") List<MultipartFile> imgFile,
                                                  @RequestParam("id") String id,
                                                  @RequestParam("name") String name){

        log.info("saveBackProfile");

        List<String> fileName = new ArrayList<>();

        try{
            for(MultipartFile files : imgFile) {
                log.info("requestUploadFile() - Make file: " + files.getOriginalFilename());

                FileOutputStream file = new FileOutputStream("../marketfront/src/assets/bossProfile/back/" + id + "_" + name +"_" + files.getOriginalFilename());

                String fileSrc = id + "_" + name +"_" +  files.getOriginalFilename();

                fileName.add(fileSrc);
                log.info(fileSrc);
                file.write(files.getBytes());
                file.close();
            }

        } catch (Exception e){
            log.info("에러");
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        bossService.saveBackImg(id, name, fileName);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/getBackProfile")
    public List<BossBackProfileImg> getBackProfile(@RequestBody Map<String, String> map){
        log.info("getBackProfile"+ map.get("id"));

        return bossService.getBackProfile(map.get("id"));
    }

}
