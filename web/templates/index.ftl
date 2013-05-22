<#include "base.ftl" />
<#macro main_container>
<h1 class="title">FAQ</h1>
<div class="ratehr"></div>
<pre>
公告：
1.course 上助教上传了新的 samples，是应李老师要求提供的质量良好的 sample，并且配备了一个本地评测的脚本
2.SLSB benchmark 是用于评测算法正确性的置信区间的，暂不考察大家在这些 benchmark 上的表现
3.现在大家相互之间可以查看 task 的详情
</pre>
<p class="ask"><strong>A: What is <strong class="ratered">RATE</strong></strong>?</p>
<pre>
    Rate is a recognition algorithm test engine developed by AILAB Peking Univ.
</pre>
<p class="ask"><strong>A: How do I test my Algorithm?</strong></p>
<pre>
    1. Login or register from top-right.
    2. Create an Algorithm.
    3. Create a version in the algorithm created in step2, upload your enroll & match executables.
    4. Choose a benchmark & run it!
</pre>
<p class="ask"><strong>A: What's the input & output of my executables?</strong></p>
<pre>
    Enroll.exe &lt;InputImageName&gt; &lt;OutputTemplateName&gt; - Read the image and write a template file.
    Match.exe &lt;Template1&gt; &lt;Template2&gt; - Read 2 template file and tell the <strong class="ratered">SCORE</strong> from STDOUT.
</pre>
<p class="ask"><strong>A: What is FNMR & FMR?</strong></p>
<pre>
    <strong class="ratered">FNMR</strong> means False Not Match Rate. Basically, it's the same concept as <strong class="ratered">FRR</strong>(False Reject Rate).
    <strong class="ratered">FMR</strong> means False Match Rate. Basically, it's the same concept as <strong class="ratered">FAR</strong>(False Accept Rate).
</pre>

</#macro>