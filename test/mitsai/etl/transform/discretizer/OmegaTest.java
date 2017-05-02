package mitsai.etl.transform.discretizer;

import mitsai.etl.transform.discretizer.omega.Omega;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junior on 3/11/16.
 */
public class OmegaTest {
    @Test public void
    createEmptyOmega_anyValueIsInZeroCategory() throws Exception {
        Omega omega = new Omega(new AttributeList());
        assertThat(omega.getCategory(123.12), is(0));
    }

    @Test public void
    oneAttributeCreate_twoCategory() throws Exception {
        AttributeList att = new AttributeListBuild()
                .with(78.9).build();

        Omega omega = new Omega(att);

        assertThat(omega.getCategory(1.1), is(0));
        assertThat(omega.getCategory(123.1), is(1));
    }

    @Test public void
    twoAttributeShouldBeMerged_twoCategory() throws Exception {
        AttributeList att = new AttributeListBuild()
                .with(12.3)
                .with(78.9).build();

        Omega omega = new Omega(att);

        assertThat(omega.getCategory(1.1), is(0));
        assertThat(omega.getCategory(123.1), is(1));
    }


    private class AttributeListBuild {
        final AttributeList att = new AttributeList();

        public AttributeListBuild with(double element) {
            att.add(element, 1);
            return this;
        }

        public AttributeList build() {
            return att;
        }
    }
}
