/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.examproject.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.Transformer;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.examproject.entity.Order;
import org.examproject.model.OrderModel;
import org.examproject.model.TotalModel;
import org.examproject.repository.OrderRepository;
import org.examproject.repository.StatusRepository;

/**
 * @author h.adachi
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    ///////////////////////////////////////////////////////////////////////////
    // Fields

    @NonNull
    private final ApplicationContext context;

    @NonNull
    private final Mapper mapper;

    @NonNull
    private final OrderRepository orderRepository;

    @NonNull
    private final StatusRepository statusRepository;

    ///////////////////////////////////////////////////////////////////////////
    // public Methods

    /**
     * show index.
     */
    @RequestMapping(value={"/", "/index"}, method=RequestMethod.GET)
    public String index(ModelMap model) {
        return "index";
    }

    /**
     * show orders list.
     */
    @RequestMapping(value="order/list", method=RequestMethod.GET)
    public String list(ModelMap model) {

        // get entity-pojo list.
        List<Order> entityList = (List<Order>) context.getBean("orderListFactory", Factory.class).create();

        // set form-pojo list.
        model.addAttribute("orderModelList", createOrderModelListBy(entityList));

        return "list";
    }

    /**
     * prepare insert.
     */
    @RequestMapping(value="order/add", method=RequestMethod.GET)
    public String prepare(ModelMap model) {

        // get form-pojo.
        OrderModel form = context.getBean(OrderModel.class);

        // set form-pojo.
        model.addAttribute("orderForm", form);

        // set state
        model.addAttribute("state", "insert");

        return "order";
    }

    /**
     * insert the order.
     */
    @RequestMapping(value="order/add", method=RequestMethod.POST)
    public String insert(
        @Valid
        @ModelAttribute(value="orderForm")
        OrderModel form,
        BindingResult result,
        ModelMap model
    ) {
        if (result.hasErrors()) {
            // set state
            model.addAttribute("state", "insert");
            return "order";
        }

        // get entity-pojo.
        Order entity = createOrderBy(form);

        // save entity-pojo.
        context.getBean("addOrderClosure", Closure.class).execute(entity);

        return "redirect:/order/list";
    }

   /**
     * prepare update.
     */
    @RequestMapping(value="order/update/{orderId}", method=RequestMethod.GET)
    public String select(
        @PathVariable
        String orderId,
        ModelMap model
    ) {
        // get entity-pojo.
        Order entity = (Order) context.getBean("codeToOrderTransformer", Transformer.class).transform(orderId);

        // get form-pojo.
        OrderModel form = createOrderModelBy(entity);

        // set form-pojo.
        model.addAttribute("orderForm", form);

        // set state
        model.addAttribute("state", "update");

        return "order";
    }

    /**
     * update the order.
     */
    @RequestMapping(value="order/update/{orderId}", method=RequestMethod.POST)
    public String update(
        @PathVariable
        String orderId,
        @Valid
        @ModelAttribute(value="orderForm")
        OrderModel form,
        BindingResult result,
        ModelMap model
    ) {
        if (result.hasErrors()) {
            // set state
            model.addAttribute("state", "update");
            return "order";
        }

        // get entity-pojo.
        Order entity = (Order) context.getBean("codeToOrderTransformer", Transformer.class).transform(orderId);

        // map form-pojo to entity-pojo.
        mapper.map(form, entity);

        // save entity-pojo.
        context.getBean("updateOrderClosure", Closure.class).execute(entity);

        return "redirect:/order/list";
    }

    /**
     * delete the order.
     */
    @RequestMapping(value="order/delete/{orderId}", method=RequestMethod.POST)
    public String delete(
        @PathVariable
        String orderId
    ) {
        // get entity-pojo.
        Order entity = (Order) context.getBean("codeToOrderTransformer", Transformer.class).transform(orderId);

        // delete entity-pojo.
        context.getBean("deleteOrderClosure", Closure.class).execute(entity);

        return "redirect:/order/list";
    }

    /**
     * show total.
     */
    @RequestMapping(value="total", method=RequestMethod.GET)
    public String total(ModelMap model) {

        // get form-pojo list.
        List<TotalModel> totalModelList = createTotalModelListBy(
            (Map<String, Long>) context.getBean("totalOrderCountMapFactory", Factory.class).create(),
            (Map<String, Long>) context.getBean("totalQuantityMapFactory", Factory.class).create()
        );

        // set form-pojo list.
        model.addAttribute("totalModelList", totalModelList);

        return "total";
    }

    ///////////////////////////////////////////////////////////////////////////
    // private Methods

    /**
     * create form-pojo from entity-pojo.
     */
    private OrderModel createOrderModelBy(Order entity) {
        OrderModel form = context.getBean(OrderModel.class);
        mapper.map(entity, form);
        return form;
    }

    /**
     * create entity-pojo from form-pojo.
     */
    private Order createOrderBy(OrderModel form) {
        Order entity = context.getBean(Order.class);
        mapper.map(form, entity);
        return entity;
    }

    /**
     * create new form-pojo list from entity-pojo list.
     */
    private List<OrderModel> createOrderModelListBy(List<Order> entityList) {
        List<OrderModel> modelList = new ArrayList<>();
        entityList.forEach((Order entity) -> {
            OrderModel model = context.getBean(OrderModel.class);
            mapper.map(entity, model);
            modelList.add(model);
        });
        return modelList;
    }

    /**
     * create new total-pojo list from some list.
     */
    private List<TotalModel> createTotalModelListBy(
        Map<String, Long> totalOrderCountMap,
        Map<String, Long> totalQuantityMap
    ) {
        List<TotalModel> totalModelList = new ArrayList<>();
        totalOrderCountMap.forEach((key, value) -> {
            TotalModel form = context.getBean(TotalModel.class);
            form.setProductName(key);
            form.setOrderCount(value);
            totalModelList.add(form);
        });
        totalQuantityMap.forEach((key, value) -> {
            TotalModel form = totalModelList.stream()
                .filter(t -> t.getProductName().equals(key))
                .findFirst().orElse(null);
            form.setQuantity(value);
        });
        return totalModelList;
    }

    ///////////////////////////////////////////////////////////////////////////
    // inner functor Classes (*it would be better to divide into service classes.)

    /**
      * select the entity by order_id.
      */
    @Service(value="codeToOrderTransformer")
    class CodeToOrderTransformer implements Transformer {
        @Override
        @Transactional
        public Object transform(Object o) {
            String orderId = (String) o;
            return orderRepository.findByOrderId(orderId);
        }
    }

    /**
      * select entities list.
      */
    @Service(value="orderListFactory")
    class OrderListFactory implements Factory {
        @Override
        @Transactional
        public Object create() {
            return orderRepository.findAll();
        }
    }

    /**
      * delete the entity.
      */
    @Service(value="deleteOrderClosure")
    class deleteOrderClosure implements Closure {
        @Override
        @Transactional
        public void execute(Object o) {
            Order entity = (Order) o;
            orderRepository.deleteById(entity.getId());
        }
    }

    /**
      * update the entity.
      */
    @Service(value="updateOrderClosure")
    class updateOrderClosure implements Closure {
        @Override
        @Transactional
        public void execute(Object o) {
            Order entity = (Order) o;
            entity.setUpdated(new Date());
            orderRepository.save(entity);
        }
    }

    /**
      * save the entity.
      */
    @Service(value="addOrderClosure")
    class AddOrderClosure implements Closure {
        @Override
        @Transactional
        public void execute(Object o) {
            Order entity = (Order) o;
            entity.setOrderId(UUID.randomUUID().toString());
            entity.setCreated(new Date());
            entity.setUpdated(new Date());
            entity.setStatus(statusRepository.findByText("Created"));
            orderRepository.save(entity);
        }
    }

    /**
     * count the total order per product.
     */
    @Service(value="totalOrderCountMapFactory")
    class TotalOrderCountMapFactory implements Factory {
        @Override
        @Transactional
        public Object create() {
            Map<String, Long> countNameMap = new HashMap<>();
            orderRepository.countOrderCountGroupByProductName().stream()
                .map(o -> (Object[]) o)
                .forEachOrdered((array) -> {
                    countNameMap.put((String) array[1], (Long) array[0]);
                });
            return countNameMap;
        }
    }

    /**
     * sum the total quantity per product.
     */
    @Service(value="totalQuantityMapFactory")
    class TotalQuantityMapFactory implements Factory {
        @Override
        @Transactional
        public Object create() {
            Map<String, Long> countNameMap = new HashMap<>();
            orderRepository.sumQuantityGroupByProductName().stream()
                .map(o -> (Object[]) o)
                .forEachOrdered((array) -> {
                    countNameMap.put((String) array[1], (Long) array[0]);
                });
            return countNameMap;
        }
    }

}
