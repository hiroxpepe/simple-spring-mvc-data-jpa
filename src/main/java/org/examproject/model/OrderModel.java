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

package org.examproject.model;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author h.adachi
 */
@Data
@Component
@Scope(value="prototype")
public class OrderModel implements Serializable {

    private String orderId;

    @Size(min=1, max=24)
    private String productName;

    @Max(100)
    @Min(1)
    private Long quantity;

    private Long status;

    public String getStatusText() {
        switch (status.intValue()) {
            case 1:
                return "Created";
            case 2:
                return "Pending";
            case 3:
                return "Confirmed";
            case 4:
                return "Failed";
        }
        return "";
    }

}
