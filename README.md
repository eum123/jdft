# 설명

xml 설정을 이용하여 data format을 변환하는 기능

자세한 내용은 document/jdft_user_guide.xml 참고


설정 
========
공통설정
<pre>
  <fc>
    <pool>
    <maxActive>8</maxActive>
    <maxIdle>2</maxIdle>
    <maxWait>5000</maxWait>
    <exhaustedAction>grow</exhaustedAction>
  </pool>

  <transform>
    <import    resource="../conf/converter/VC_SETTLE_1XXX_REQ.xml" />
  </transform>
  </fc>

</pre>
 
전문 변환 설정
<pre>
  <conversion id="reqSampleTrans">
  <from format="fixedlength">
    <data>
      <header>
        <field id="HDR_LENGTH"        size="4"  description="전체길이"/>
        <field id="HDR_CORP_ID"       size="8"  description="기관코드"/>
      </header>
      <body>
         <field id="BDY_FILLER"       size="130"  description="filler"/>
      </body>
    </data>
   </from>
   <to format="fixedlength">
    <data>
      <header>
        <field id="HDR_LENGTH"        size="4"  description="전체길이"/>
        <field id="HDR_CORP_ID"       size="8"  description="기관코드"/>
      </header>
      <body>
         <field id="BDY_FILLER"       size="130"  description="filler"/>
      </body>
    </data>
   </to>
  </conversion>

</pre>

 
Dependency
=============
 <pre>
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.6.4</version>
    <scope>compile</scope>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.6.4</version>
    <scope>compile</scope>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jcl</artifactId>
    <version>1.6.4</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>org.jdom</groupId>
    <artifactId>jdom</artifactId>
    <version>1.1.3</version>
    <scope>compile</scope>
</dependency>
<dependency>
      <groupId>com.googlecode.concurrentlinkedhashmap</groupId>
     <artifactId>concurrentlinkedhashmap-lru</artifactId>
     <version>1.3.2</version>
     <scope>compile</scope>
</dependency>

<dependency>
    <groupId>commons-pool</groupId>
    <artifactId>commons-pool</artifactId>
    <version>1.6</version>
    <scope>compile</scope>
</dependency>

 </pre>
 








개인정보
---------

email : eummanjin@gmail.com




