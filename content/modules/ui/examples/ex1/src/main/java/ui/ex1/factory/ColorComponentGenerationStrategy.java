package ui.ex1.factory;

import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.core.metamodel.model.Range;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.ColorPicker;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.ComponentGenerationContext;
import io.jmix.ui.component.ComponentGenerationStrategy;
import io.jmix.ui.component.data.ValueSource;
import org.springframework.core.annotation.Order;
import ui.ex1.entity.ColorDatatype;

import javax.annotation.Nullable;
import javax.inject.Inject;

//tag::strategy[]
@Order(100)
@org.springframework.stereotype.Component("sample_ColorComponentGenerationStrategy")
public class ColorComponentGenerationStrategy implements ComponentGenerationStrategy {

    @Inject
    private UiComponents uiComponents;

    @Nullable
    @Override
    public Component createComponent(ComponentGenerationContext context) {
        String property = context.getProperty();
        MetaPropertyPath mpp = context.getMetaClass().getPropertyPath(property);

        if (mpp != null) {
            Range mppRange = mpp.getRange();
            if (mppRange.isDatatype()
                    && (Datatype) mppRange.asDatatype() instanceof ColorDatatype) {
                ColorPicker colorPicker = uiComponents.create(ColorPicker.class);
                colorPicker.setDefaultCaptionEnabled(true);

                ValueSource valueSource = context.getValueSource();
                if (valueSource != null) {
                    colorPicker.setValueSource(valueSource);
                }
                return colorPicker;
            }
        }
        return null;
    }

}
//end::strategy[]
