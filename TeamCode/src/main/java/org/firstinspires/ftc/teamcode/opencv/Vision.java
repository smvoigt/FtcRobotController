package org.firstinspires.ftc.teamcode.opencv;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class Vision {
    OpenCvCamera camera;
    SplitAveragePipeline splitAveragePipeline;
    int camW = 640;
    int camH = 480;

    public Vision(HardwareMap hardwareMap, Telemetry telemetry) {
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"));
        splitAveragePipeline = new SplitAveragePipeline(telemetry, camW, camH);

        camera.setPipeline(splitAveragePipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(camW, camH, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    public int elementDetection() {
        return splitAveragePipeline.get_element_zone();
    }
}