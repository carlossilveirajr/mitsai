package mitsai.etl.transform.discretizer;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 3/10/16.
 */
public class AttributeListTest {
    private static final int CATEGORY_1 = 1;
    private static final int CATEGORY_2 = 2;
    private static final double VALUE_1 = 3.141565;
    private static final double VALUE_2 = 2.7375;

    private AttributeList attributeList;

    @Before
    public void setUp() throws Exception {
        attributeList = new AttributeList();
    }

    @Test(expected = AttributeList.NegativeCategory.class) public void
    addAttributeWithNegativeCategory_throwsException() throws Exception {
        attributeList.add(VALUE_1, -1);
    }

    @Test public void
    addAttribute_sizeEqualsAttributes() throws Exception {
        attributeList.add(VALUE_1, CATEGORY_1);
        assertThat(attributeList.size(), is(1));
    }

    @Test public void
    addTwoEqualAttributesSameCategory_sizeEqualsToOne() throws Exception {
        attributeList.add(VALUE_1, CATEGORY_1);
        attributeList.add(VALUE_1, CATEGORY_1);
        assertThat(attributeList.size(), is(1));
    }

    @Test public void
    addTwoEqualAttributesDifferentCategory_sizeEqualsToTwo() throws Exception {
        attributeList.add(VALUE_1, CATEGORY_1);
        attributeList.add(VALUE_1, CATEGORY_2);
        assertThat(attributeList.size(), is(2));
    }

    @Test public void
    addTwoDifferentAttributesAndCategory_sizeEqualsToTwo() throws Exception {
        attributeList.add(VALUE_1, CATEGORY_1);
        attributeList.add(VALUE_2, CATEGORY_2);
        assertThat(attributeList.size(), is(2));
    }

    @Test public void
    listMustBeSortedBasedOnValue() throws Exception {
        attributeList.add(VALUE_1, CATEGORY_1);
        attributeList.add(VALUE_2, CATEGORY_2);

        Attribute [] set = attributeList.getSortedAttributes();
        assertThat((set[0].value <= set[1].value), is(Boolean.TRUE));
    }
}
