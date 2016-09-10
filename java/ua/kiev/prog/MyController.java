package ua.kiev.prog;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MyController {

    private Map<Long, byte[]> photos = new HashMap<Long, byte[]>();

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam("phots") MultipartFile[] files) {
        Map<Long, byte[]> temp = new HashMap<>();
        for (MultipartFile file : files) {
            if (file.isEmpty())
                throw new PhotoErrorException();
            try {
                long id = System.currentTimeMillis();
                photos.put(id, file.getBytes());
                temp.put(id, file.getBytes());
            } catch (IOException e) {
                throw new PhotoErrorException();
            }
        }
        model.addAttribute("map", temp);
        return "result";
    }

    @RequestMapping(value = "/view_list")
    public String viewAll(Model model) {
        model.addAttribute("photo_numb", photos.size());
        model.addAttribute("map", photos);
        return "view";
    }

    @RequestMapping(value = "/deleteChecked", method = RequestMethod.POST)
    public void deleteChecked(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        for (String key : req.getParameterMap().keySet()) {
            photos.remove(Long.parseLong(key));
        }
        resp.sendRedirect("/view_list");
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public String deleAll(HttpServletRequest req) {
        for (String key : req.getParameterMap().keySet()) {
            System.out.println(key);
        }
        return "index";
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        if (photos.remove(id) == null)
            throw new PhotoNotFoundException();
        else
            return "index";
    }

    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = photos.get(id);
        if (bytes == null)
            throw new PhotoNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
}
