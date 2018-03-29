package net.unit8.rascaloid;

import enkan.component.ComponentLifecycle;
import enkan.component.SystemComponent;
import lombok.Getter;
import lombok.Setter;

public class RascaloidConfiguration extends SystemComponent<RascaloidConfiguration> {
    @Getter
    @Setter
    private String basePath = "";

    @Override
    protected ComponentLifecycle<RascaloidConfiguration> lifecycle() {
        return new ComponentLifecycle<RascaloidConfiguration>() {
            @Override
            public void start(RascaloidConfiguration config) {

            }

            @Override
            public void stop(RascaloidConfiguration config) {

            }
        };
    }
}
