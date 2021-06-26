package org.firstinspires.ftc.teamcode.edubot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.paladins.common.ButtonControl;
import org.firstinspires.ftc.teamcode.paladins.common.PaladinsComponent;
import org.firstinspires.ftc.teamcode.paladins.common.PaladinsOpMode;


/**
 * Operation to assist with Gamepad actions on DCMotors
 */
public class EduBotArmController extends PaladinsComponent {

    private final ButtonControl forwardButtonControl;
    private final ButtonControl reverseButtonControl;

    private final DcMotor motor;
    private final Gamepad gamepad;
    private final TouchSensor stopSensor;
    private final float motorPower;
    private final Telemetry.Item item;
    private boolean showtelemetry = false;

    /**
     * Constructor for operation.  Telemetry enabled by default.
     *
     * @param opMode
     * @param gamepad              Gamepad
     * @param config               EduBotConfiguration
     * @param forwardButtonControl {@link ButtonControl}
     * @param reverseButtonControl {@link ButtonControl}
     * @param power                power to apply when using gamepad buttons
     * @param showTelemetry        display the power values on the telemetry
     */
    public EduBotArmController(PaladinsOpMode opMode, Gamepad gamepad, EduBotConfiguration config,
                               ButtonControl forwardButtonControl, ButtonControl reverseButtonControl,
                               float power, boolean showTelemetry) {
        super(opMode);

        this.gamepad = gamepad;
        this.motor = config.armMotor;
        this.forwardButtonControl = forwardButtonControl;
        this.reverseButtonControl = reverseButtonControl;
        this.motorPower = power;
        this.stopSensor = config.touchSensor;

        if (showTelemetry) {
            item = opMode.telemetry.addData("Arm " + forwardButtonControl.name() + "/" + reverseButtonControl, new Func<Double>() {
                @Override
                public Double value() {
                    return motor.getPower();
                }
            });
            item.setRetained(true);
        } else {
            item = null;
        }
    }


    public EduBotArmController(PaladinsOpMode opMode, Gamepad gamepad, EduBotConfiguration config,
                               ButtonControl forwardButtonControl, ButtonControl reverseButtonControl,
                               float power) {
        this(opMode, gamepad, config, forwardButtonControl, reverseButtonControl, power, true);
    }

    /**
     * Update motors with latest gamepad state
     */
    public void update() {
        if (buttonPressed(gamepad, forwardButtonControl)) {
            motor.setPower(motorPower);
        } else  if (stopSensor != null && stopSensor.isPressed()) {
            motor.setPower(0.0);
        } else if (buttonPressed(gamepad, reverseButtonControl)) {
            motor.setPower(-motorPower);
        } else {
            motor.setPower(0.0);
        }
    }
}
