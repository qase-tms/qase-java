package io.qase.api.models.v1.defects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Defects {
    private long count;
    @SerializedName("entities")
    private List<Defect> defectList;
    private long filtered;
    private long total;
}
