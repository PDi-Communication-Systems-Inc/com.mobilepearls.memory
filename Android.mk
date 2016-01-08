LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-java-files-under)
LOCAL_PACKAGE_NAME := com.mobilepearls.memory
LOCAL_MODULE_TAGS := optional
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)
