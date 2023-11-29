package com.onlineshop.user.api.model;

import com.onlineshop.store.api.model.MultimediaModel;
import com.onlineshop.store.api.model.TagModel;
import com.onlineshop.store.api.model.VendorModel;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemFromStorageModel {
    private String id;
    private String title;
    private String description;
    private VendorModel vendor;
    private List<MultimediaModel> multimedia;
    private List<TagModel> tags;
    private Long quantity;
    private BigDecimal price;
}
