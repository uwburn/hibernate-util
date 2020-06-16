package it.mgt.util.hibernate.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class HibernateModule extends SimpleModule {

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addSerializers(new HibernateSerializers());
    }
}
