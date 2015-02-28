package com.apollo.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*klmklmmk*
 * Created by mrugen on 2/28/15.
 */
@RestController
@RequestMapping("/alerts")
public class AlertResource {

    @RequestMapping("/1")
    public String fetchAlerts(){
        return "alerts";
    }


}
