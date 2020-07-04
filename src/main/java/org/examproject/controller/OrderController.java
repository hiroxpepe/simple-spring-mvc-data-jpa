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
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.examproject.entity.Order;
import org.examproject.model.OrderModel;
import org.examproject.repository.OrderRepository;
import org.examproject.response.OrderResponse;

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

    ///////////////////////////////////////////////////////////////////////////
    // public Methods

    @RequestMapping(
        value={"/", "/index"},
        method=RequestMethod.GET
    )
    public String index(ModelMap model) {
        return "index";
    }

    @RequestMapping(
        value="/list",
        method=RequestMethod.GET,
        produces="application/json"
    )
    @ResponseBody
    public OrderResponse list(ModelMap model) {
        // select orders.

        // get order-entity list.
        List<Order> entityList = orderRepository.findAll();

        // return the response-object.
        return addToResponse(entityList, context.getBean(OrderResponse.class));
    }

    ///////////////////////////////////////////////////////////////////////////
    // private Methods

    private OrderResponse addToResponse(
        List<Order> entityList, OrderResponse response
    ) {
        List<OrderModel> modelList = new ArrayList<>();
        // process the entry object of all of the list.
        entityList.forEach((Order entity) -> {
            // create a object to send to the html page.
            OrderModel model = context.getBean(OrderModel.class);
            // map the value to the object.
            mapper.map(
                entity,
                model
            );
            // add the object to the object list.
            modelList.add(
                model
            );
        });

        // set the object list to response-object.
        response.setModelList(modelList);

        // set the error status.
        response.setIsError(false);

        return response;
    }

}
