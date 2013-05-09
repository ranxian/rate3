<#include "base.ftl" />
<#macro main_container>
<h1 class="title">FAQ</h1>
<div class="ratehr"></div>
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

</#macro>