package com.epi.challenge.domain.utilities;



import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Saber Ben Khalifa
 */
public class EntityUtil {

    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) //null==null || id==id
        {
            return true;
        }

        if (obj1 == null || obj2 == null) // obj1 XOR obj2 is null
        {
            return false;
        }

        final Class<? extends Object> obj1Class = obj1.getClass();
        final Class<? extends Object> obj2Class = obj2.getClass();
        if (!obj1Class.equals(obj2Class)) {
            return false;
        }

        final Map<String, IntrospectionUtils.ClassFieldMetadata> fieldsMetadata = IntrospectionUtils.SHARED_INSTANCE.getFieldsMetadata(obj1Class);
        Set<String> containerFields = null;
        for (Entry<String, IntrospectionUtils.ClassFieldMetadata> entry : fieldsMetadata.entrySet()) {
            IntrospectionUtils.ClassFieldMetadata fieldMetadata = entry.getValue();
            if (fieldMetadata.isCollection() || fieldMetadata.isMap()) {
                // do it later on
                if (containerFields == null) {
                    containerFields = new HashSet<>();
                }
                containerFields.add(entry.getKey());
            } else {
                final Object fieldValue1 = fieldMetadata.getValue(obj1);
                final Object fieldValue2 = fieldMetadata.getValue(obj2);
                if (!Objects.equals(fieldValue1, fieldValue2)) {
                    return false;
                }
            }
        }

        if (containerFields == null) {
            return true;
        }

        // do container equals in the second step
        for (String fieldName : containerFields) {
            final IntrospectionUtils.ClassFieldMetadata fieldMetadata = fieldsMetadata.get(fieldName);
            final Object fieldValue1 = fieldMetadata.getValue(obj1);
            final Object fieldValue2 = fieldMetadata.getValue(obj2);
            if (!Objects.equals(fieldValue1, fieldValue2)) {
                return false;
            }
        }

        return true;
    }
}
