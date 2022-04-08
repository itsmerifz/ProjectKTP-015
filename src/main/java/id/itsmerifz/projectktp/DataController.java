package id.itsmerifz.projectktp;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataController {

  DataJpaController dataCtrl = new DataJpaController();
  List<Data> newData = new ArrayList<>();

  @RequestMapping("/main")
  public String getMain(){
    return "menu";
  }
  
  @RequestMapping("/data")
  public String getDataKTP(Model m){
    int r = dataCtrl.getDataCount();
    try{
      newData = dataCtrl.findDataEntities().subList(0, r);
    }catch(Exception e){

    }
    m.addAttribute("data", newData);
    return "database";
  }

  @RequestMapping("/edit")
  public String doEdit(){
    return "editktp";
  }
}
