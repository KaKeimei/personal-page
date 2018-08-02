#   Patchca: verification code library for Java


### Overview

Simple yet powerful verification code library written in Java with zero dependency.

You can generate verification code picture like this:

```java
ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));

FileOutputStream fos = new FileOutputStream("patcha_demo.png");
EncoderHelper.getChallangeAndWriteImage(cs, "png", fos);
fos.close();
```

it generate picture like this:    
![sample](https://raw.githubusercontent.com/pusuo/patchca/master/doc/images/patcha_demo.png)

