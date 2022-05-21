package id.itsmerifz.projectktp.dummy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DummyController {
  DummyJpaController dummyCtrl = new DummyJpaController();
  List<Dummy> data = new ArrayList<>();

  @RequestMapping("/read")
  public String getDummy(Model m) {
    try {
      data = dummyCtrl.findDummyEntities();

    } catch (Exception e) {

    }
    m.addAttribute("data", data);
    return "dummy";
  }

  @RequestMapping("/create")
  public String createDummy() {
    return "dummy/create";
  }

  @PostMapping(value = "/newdata", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public String newDummyData(@RequestParam("gambar") MultipartFile f, HttpServletRequest r)
      throws ParseException, Exception {
    Dummy d = new Dummy();

    int id = Integer.parseInt(r.getParameter("id"));
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(r.getParameter("tanggal"));
    byte[] img = f.getBytes();
    d.setId(id);
    d.setTanggal(date);
    d.setGambar(img);

    dummyCtrl.create(d);
    return "created";
  }

  @RequestMapping(value = "/img", method = RequestMethod.GET, produces = {
      MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE
  })
  public ResponseEntity<byte[]> getImg(@RequestParam("id") int id) throws Exception {
    Dummy d = dummyCtrl.findDummy(id);
    byte[] img = d.getGambar();
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(img);
  }

  @GetMapping("/delete/{id}")
  @ResponseBody
  public String deleteDummy(@PathVariable("id") int id) throws Exception {
    dummyCtrl.destroy(id);
    return "deleted";
  }

  @RequestMapping("/edit/{id}")
  public String updateDummy(@PathVariable("id") int id, Model m) throws Exception {
    Dummy d = dummyCtrl.findDummy(id);
    m.addAttribute("data", d);
    return "dummy/update";
  }

  @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public String updateDummyData(@RequestParam("gambar") MultipartFile f, HttpServletRequest r)
      throws ParseException, Exception {
    Dummy d = new Dummy();

    int id = Integer.parseInt(r.getParameter("id"));
    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(r.getParameter("tanggal"));
    byte[] img = f.getBytes();
    d.setId(id);
    d.setTanggal(date);
    d.setGambar(img);

    dummyCtrl.edit(d);
    return "updated";
  }
}
