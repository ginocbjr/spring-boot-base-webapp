package org.networking.web.controller;

import org.networking.entity.BaseEntity;
import org.networking.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gino on 9/20/2015.
 */
@Controller
public abstract class BaseController<T extends BaseEntity> {

    @Autowired
    protected BaseService<T> baseService;

    @RequestMapping(value = "/{id}", produces = {"application/json"})
    public @ResponseBody T get(@PathVariable Long id) {
        return baseService.load(id);
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST}, produces = {"application/json"}, consumes = {"application/json"})
    public @ResponseBody Map<String, Object> create(@RequestBody T t) {
        Map<String, Object> map = new HashMap<>();
        baseService.save(t);
        map.put("object", t);
        return map;
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT}, produces = {"application/json"}, consumes = {"application/json"})
    public @ResponseBody Map<String, Object> update(@PathVariable Long id, @RequestBody T t) {
        Map<String, Object> map = new HashMap<>();
        T inDb = baseService.load(id);
        BeanUtils.copyProperties(t, inDb);
        baseService.save(inDb);
        map.put("object", inDb);
        return map;
    }

}
