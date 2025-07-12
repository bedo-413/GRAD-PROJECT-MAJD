package jordan.university.gradproject2.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddRemarksRequest {
    private List<String> remarks;
}