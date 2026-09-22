package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
 * This OpMode ramps a single motor speed up and down repeatedly until Stop is pressed.
 * The code is structured as a LinearOpMode
 *
 * This code assumes a DC motor configured with the name "left_drive" as is found on a Robot.
 *
 * INCREMENT sets how much to increase/decrease the power each cycle
 * CYCLE_MS sets the update period.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
@TeleOp(name = "Concept: Ramp Motor Speed", group = "Concept")
@Disabled
public class TestMotor extends LinearOpMode
{

    static final double INCREMENT   = 0.01;     // amount to ramp motor each CYCLE_MS cycle
    static final int    CYCLE_MS    =   50;     // period of each cycle
    static final double MAX_FWD     =  1.0;     // Maximum FWD power applied to motor
    static final double MAX_REV     = -1.0;     // Maximum REV power applied to motor

    static double time, initVel, initPos;
    static final double posFunction = -4.9 * Math.pow(time, 2) + initVel * time + initPos;


        /**
         * Calculates the signed angle difference between a current heading vector
         * and a target heading vector.
         *
         * @param currentX X component of the motor's current orientation vector
         * @param currentY Y component of the motor's current orientation vector
         * @param targetX  X component of the desired target vector
         * @param targetY  Y component of the desired target vector
         * @return The angle error in DEGREES.
         *         Positive means turn counter-clockwise, Negative means turn clockwise.
         */
        public double calculateMotorAngleError(double currentX, double currentY, double targetX, double targetY) {
            // 1. Calculate the dot product (A . B)
            double dotProduct = (currentX * targetX) + (currentY * targetY);

            // 2. Calculate the cross product magnitude (Z-component of A x B)
            // This gives us the directional sign of the angle change
            double crossProductZ = (currentX * targetY) - (currentY * targetX);

            // 3. Use atan2(y, x) -> atan2(cross, dot) to get the signed angle in radians
            double angleInRadians = Math.atan2(crossProductZ, dotProduct);

            // 4. Convert to degrees for easier motor/PID control processing
            return Math.toDegrees(angleInRadians);
    }



    // Define class members
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    double  power   = 0;
    boolean rampUp  = true;


    @Override
    public void runOpMode() {

        // Connect to motor (Assume standard left wheel)
        // Change the text in quotes to match any motor name on your robot.


        // Wait for the start button
        telemetry.addData(">", "Press Start to run Motors." );
        telemetry.update();
        waitForStart();

        // Ramp motor speeds till stop pressed.
        while(opModeIsActive()) {

            // Ramp the motors, according to the rampUp variable.
            if (rampUp) {
                // Keep stepping up until we hit the max value.
                power += INCREMENT ;
                if (power >= MAX_FWD ) {
                    power = MAX_FWD;
                    rampUp = !rampUp;   // Switch ramp direction
                }
            }
            else {
                // Keep stepping down until we hit the min value.
                power -= INCREMENT ;
                if (power <= MAX_REV ) {
                    power = MAX_REV;
                    rampUp = !rampUp;  // Switch ramp direction
                }
            }

            // Display the current value
            telemetry.addData("Motor Power", "%5.2f", power);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            // Set the motor to the new power and pause;

            sleep(CYCLE_MS);
            idle();
        }

        // Turn off motor and signal done;
        telemetry.addData(">", "Done");
        telemetry.update();

    }
}
