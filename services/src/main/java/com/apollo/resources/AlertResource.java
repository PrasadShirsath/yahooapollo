package com.apollo.resources;

import com.apollo.services.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/*klmklmmk*
 * Cfmmfsmfskdleated by mrugen on 2/28/15.
 */
@RestController

public class AlertResource {
    private final AlertService alertService;

    @Autowired
    public AlertResource(AlertService service) {
        this.alertService = service;
    }

    @RequestMapping("/alerts")
    public Map<String, String> fetchAlerts() {
        return alertService.fetchActionableAlerts();
    }


}