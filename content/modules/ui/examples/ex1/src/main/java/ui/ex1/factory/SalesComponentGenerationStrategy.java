package ui.ex1.factory;

import io.jmix.core.Metadata;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.component.data.ValueSource;
import org.springframework.core.Ordered;
import ui.ex1.entity.Order;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.sql.Date;

// tag::strategy[]
@org.springframework.stereotype.Component("sample_SalesComponentGenerationStrategy")
public class SalesComponentGenerationStrategy implements ComponentGenerationStrategy, Ordered {

    @Inject
    private UiComponents uiComponents;

    @Inject
    private Metadata metadata;

    @Nullable
    @Override
    public Component createComponent(ComponentGenerationContext context) {
        String property = context.getProperty();
        MetaClass orderMetaClass = metadata.getClass(Order.class);

        if (orderMetaClass.equals(context.getMetaClass())
                && "date".equals(property) // <1>
                && context.getClass() != null
                && Form.class.isAssignableFrom(context.getClass())) { // <2>
            DatePicker<Date> datePicker = uiComponents.create(DatePicker.TYPE_DATE); // <3>

            ValueSource valueSource = context.getValueSource();
            if (valueSource != null) {
                datePicker.setValueSource(valueSource); // <4>
            }

            return datePicker;
        }

        return null;
    }

    @Override
    public int getOrder() {
        return 50; // <5>
    }
}
//end::strategy[]