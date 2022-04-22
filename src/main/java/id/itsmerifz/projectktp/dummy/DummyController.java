package id.itsmerifz.projectktp.dummy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DummyController {
  DummyJpaController dummyCtrl = new DummyJpaController();
  List<Dummy> data = new ArrayList<>();

  @RequestMapping("/read")
  @ResponseBody
  public List<Dummy> getDummy(){
    try{
      data = dummyCtrl.findDummyEntities();
    }catch(Exception e){

    }
    return data;
  }

  @RequestMapping("/create")
  public String createDummy(){
    return "dummy/create";
  }

  @PostMapping(value = "/newData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public String newDummyData(@RequestParam("gambar") MultipartFile f, HttpServletRequest r) throws ParseException, Exception {
    Dummy d = new Dummy();

    int id = Integer.parseInt(r.getParameter("id"));
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(r.getParameter("tanggal"));
    // String filename = StringUtils.cleanPath(f.getOriginalFilename());
    byte[] img = f.getBytes();
    d.setId(id);
    d.setTanggal(date);
    d.setGambar(img);

    dummyCtrl.create(d);
    return "created";
  }
}
