<div class="control-group">
    <div class="control-label">
        <label>Name</label>
    </div>
    <div class="controls">
    <@s.textfield name="algorithm.name" label="Name"></@s.textfield>
    </div>
</div>

<div class="control-group">
    <div class="control-label">
        <label>Type</label>
    </div>
    <div class="controls">
    <@s.select label="Type"
    name="algorithm.type"
    list="{'FINGERVEIN'}"
                    ></@s.select>
    </div>
</div>

<div class="control-group">
    <div class="control-label">
        <label>Protocol</label>
    </div>
    <div class="controls">
    <@s.select label="Protocol"
    name="algorithm.protocol"
    list="{'PKURATE'}"
                ></@s.select>
    </div>
</div>

<div class="control-group">
    <div class="control-label">
        <label>Description</label>
    </div>
    <div class="controls">
    <@s.textarea label="Description"
    name="algorithm.description" ></@s.textarea>
    </div>
</div>

<div class="form-actions">
<@s.submit cssClass="btn"></@s.submit>
</div>