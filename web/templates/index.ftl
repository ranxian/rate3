<#include "base.ftl" />
<#macro main_container>
<h1 class="title">FAQ</h1>
<div class="ratehr"></div>
<pre>
公告：
<h3>1. 有一个文档齐全的 <a href="/static/samples.zip" class="ratered"><strong>sample.zip</strong></a>，里面包含了一些有用的 tips</h3>
2. 大家务必用 VS 中的 release 模式编译，否则可能会跑相当之久
3. OpenCV 尚未支持，欢迎发送 OpenCV 版算法的源代码 + VS 编译的可执行文件到 xranthoar@gmail.com，附上你的 OpenCV、VS 版本
4. 最近服务器不是特别稳定，如果出现这种情况管理员会重新 run 失败的测试（也就是 '50%直线')
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
<p class="ask"><strong>A: 感觉搞不动啊</strong></p>
<pre>
    这里有两篇不错的论文：
    <a href="/static/finger vein recognition by Huang Beining.pdf" class="ratered"><strong>finger vein recognition by Huang Beining</strong></a>
    <a href="/static/finger posture correction.pdf" class="ratered"><strong>finger posture correction</strong></a>
</pre>

</#macro>