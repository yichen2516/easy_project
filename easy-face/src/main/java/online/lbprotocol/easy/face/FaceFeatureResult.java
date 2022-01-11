package online.lbprotocol.easy.face;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.GenderInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author yichen for easy_project
 * @since 2022/1/11
 */
@AllArgsConstructor
@Getter
public class FaceFeatureResult {
    private List<FaceFeature> faceFeatures;
    private List<AgeInfo> ageInfos;
    /**
     * 未知性别=-1 、男性=0 、女性=1
     */
    private List<GenderInfo> genderInfos;
}
