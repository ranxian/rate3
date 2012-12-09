package rate.engine.view.GenerateStrategy;

import rate.model.SampleEntity;

import java.util.List;
import java.util.UUID;

/**
 * User:    Yu Yuankai
 * Email:   yykpku@gmail.com
 * Date:    12-12-9
 * Time:    下午4:47
 */
abstract public class AbstractGenerateStrategy{
    abstract public List<SampleEntity> getSamples() throws GenerateStrategyException;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    private String viewName;

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    private String generator;
}
