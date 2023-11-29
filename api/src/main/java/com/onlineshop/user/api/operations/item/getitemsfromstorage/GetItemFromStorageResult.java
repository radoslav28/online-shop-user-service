package com.onlineshop.user.api.operations.item.getitemsfromstorage;

import com.onlineshop.store.api.model.MultimediaModel;
import com.onlineshop.store.api.model.TagModel;
import com.onlineshop.store.api.model.VendorModel;
import com.onlineshop.user.api.base.ProcessorResult;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetItemFromStorageResult implements ProcessorResult {
    private String id;
    private String title;
    private String description;
    private VendorModel vendor;
    private List<MultimediaModel> multimedia;
    private List<TagModel> tags;
    private Long quantity;
    private BigDecimal price;
}
