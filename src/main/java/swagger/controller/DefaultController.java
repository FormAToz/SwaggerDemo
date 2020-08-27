package swagger.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = "Отображение swaggerAPI и выход из приложения")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DefaultController {

    @Value("${application.swaggerHome}")
    private String swaggerHome;

    @GetMapping
    public ModelAndView getSwaggerInfo() {
        return new ModelAndView("redirect:" + swaggerHome);
    }

    /**
     * Производит закрытия приложение с редиректом на страницу /exit-success с надписью ‘приложение закрыто’
     */
    @GetMapping("/exit")
    public ModelAndView redirect() {
        return new ModelAndView("redirect:exit-success");
    }

    @GetMapping("/exit-success")
    public String exit() {
        return "Приложение закрыто";
    }
}
