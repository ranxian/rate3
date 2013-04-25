<div class="control-group">
    <label class="control-label">Description</label>
    <div class="controls">
    <@s.textarea name="algorithmVersion.description" ></@s.textarea>
    </div>
</div>

<div class="control-group">
    <label class="control-label">Enroll.exe</label>
    <div class="controls">
        <input type="file" name="enrollExe">
    </div>
</div>

<div class="control-group">
    <label class="control-label">Match.exe</label>
    <div class="controls">
        <input type="file" name="matchExe">
    </div>
</div>
<div class="form-actions">
<@s.submit cssClass="btn" value="Import"></@s.submit>
</div>