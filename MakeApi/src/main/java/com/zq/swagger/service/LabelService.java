package com.zq.swagger.service;


import com.zq.swagger.dao.LabelDao;
import com.zq.swagger.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签业务逻辑类
 */
@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll(){
        return labelDao.findAll();
    }

    public Label findById(String id){
        return labelDao.findById(id).get();
    }
    public void add (Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }
    public void update (Label label){
        labelDao.save(label);
    }
    public void delateById (String id){
        labelDao.deleteById(id);
    }

    public Object findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname()))
                    predicateList.add(cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"));
                if (label.getState() != null && !"".equals(label.getState()))
                    predicateList.add(cb.equal(root.get("state").as(String.class),  label.getState()));
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        });
    }


    public Page findPage(Label label, int page, int size) {
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname()))
                    predicateList.add(cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"));
                if (label.getState() != null && !"".equals(label.getState()))
                    predicateList.add(cb.equal(root.get("state").as(String.class),  label.getState()));
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        }, PageRequest.of(page-1,size));
    }
}
