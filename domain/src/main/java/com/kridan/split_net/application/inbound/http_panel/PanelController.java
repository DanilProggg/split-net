package com.kridan.split_net.application.inbound.http_panel;

import com.kridan.split_net.domain.gateway.Gateway;
import com.kridan.split_net.domain.site.Site;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/panel")
public class PanelController {
    @GetMapping
    public String index() {
        return "index"; // => templates/index.html
    }

    @GetMapping("/gateways")
    public String gateways(Model model) {
        model.addAttribute("gateways", List.of(
                new Gateway("gw-1", "10.0.0.1", new Site())
        ));
        return "fragments/gateways :: table"; // => templates/fragments/gateways.html
    }
}
