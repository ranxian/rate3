<label class="control-label">Name</label>
<div class="control-group">
    <div class="controls">
        <input type="text" name="view.name" required />
    </div>
</div>
<div class="control-group">
    <label class="control-label">Type</label>
    <div class="controls">
    <@s.select
                      name="view.type"
    list="{'FINGERVEIN'}"
    headerKey="0"
            ></@s.select>
    </div>
</div>
<div class="control-group">
    <label class="control-label">Description</label>
    <div class="controls">
    <@s.textarea name="view.description"></@s.textarea>
    </div>
</div>
<div class="control-group">
    <div class="controls">
        <button type="submit" class="btn">Submit</button>
    </div>
</div>