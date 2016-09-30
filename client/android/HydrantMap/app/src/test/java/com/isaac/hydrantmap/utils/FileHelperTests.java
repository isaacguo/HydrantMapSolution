package com.isaac.hydrantmap.utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by yueguo01 on 3/12/2016.
 */
public class FileHelperTests
{
    @Test
    public void FileHelperTest_getHydrantDirs() throws Exception
    {
        String path=FileHelper.getHydrantFolder().getPath();
        Assert.assertEquals("sdcard/com.isaac.hydrant",path);
    }
}
