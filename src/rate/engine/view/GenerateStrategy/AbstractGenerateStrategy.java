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
}
