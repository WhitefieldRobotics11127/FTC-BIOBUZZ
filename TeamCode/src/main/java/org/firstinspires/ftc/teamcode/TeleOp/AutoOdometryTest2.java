package org.firstinspires.ftc.teamcode.TeleOp;


/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

/*
 * This file contains an example of a Linear "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode is executed.
 *
 * This particular OpMode illustrates driving a 4-motor Omni-Directional (or Holonomic) robot.
 * This code will work with either a Mecanum-Drive or an X-Drive train.
 * Both of these drives are illustrated at https://gm0.org/en/latest/docs/robot-design/drivetrains/holonomic.html
 * Note that a Mecanum drive must display an X roller-pattern when viewed from above.
 *
 * Also note that it is critical to set the correct rotation direction for each motor.  See details below.
 *
 * Holonomic drives provide the ability for the robot to move in three axes (directions) simultaneously.
 * Each motion axis is controlled by one Joystick axis.
 *
 * 1) Axial:    Driving forward and backward               Left-joystick Forward/Backward
 * 2) Lateral:  Strafing right and left                     Left-joystick Right and Left
 * 3) Yaw:      Rotating Clockwise and counter clockwise    Right-joystick Right and Left
 *
 * This code is written assuming that the right-side motors need to be reversed for the robot to drive forward.
 * When you first test your robot, if it moves backward when you push the left stick forward, then you must flip
 * the direction of all 4 motors (see code below).
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="Auto 1 -USE-" +
        "", group="Auto")
//@Disabled
public class AutoOdometryTest2 extends LinearOpMode {




    // Create a RobotHardware object to be used to access robot hardware.
    // Prefix any hardware functions with "robot." to access this class.
    RobotHardware robot = new RobotHardware(this);

    // Declare OpMode members.

    private ElapsedTime runtime = new ElapsedTime();

    private final double NORMAL = RobotHardware.MOTOR_SPEED_FACTOR_NORMAL;
    private final double FAST = RobotHardware.MOTOR_SPEED_FACTOR_DAVIS;

    int delay = 1500;
    @Override
    public void runOpMode() {

        // initialize all the hardware, using the hardware class. See how clean and simple this is?
        robot.init();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();

        // Reset the runtime timer
        runtime.reset();

        if (opModeIsActive()) {
            sleep(delay);
            // Execute the 4-ball shooting and driving autonomous routine
            fourBallShootAndDriveAuto();
        }

        // Make sure robot stops before OpMode dies
        robot.stop();
    }

    private void fourBallShootAndDriveAuto() {
        if (!opModeIsActive()) return;

        // Step 1: Shoot all 4 balls currently in the robot
        robot.forwardSizzleSteak2(.82);
        sleep(1000);                  // Wait for flywheel to spin up to max speed
        robot.forwardSizzleSteak(1);
        robot.shootOn(robot.currLaunchVel);// Feed balls into the launcher
        sleep(5000);                  // Duration to feed all 4 balls
        robot.sizzleSteakOff();
        robot.sizzleSteakOff2();// Stop feeder
        robot.shootOff();             // Turn off shooter

        // Step 2: Rotate 90 degrees counterclockwise
        robot.turnCustom(Math.PI / 2, NORMAL);

        // Step 3: Go forward 4ft (4 ft * 304.8 mm/ft ≈ 1219 mm) and intake balls (intake code omitted)
        robot.forward(1219, NORMAL);

        // Step 4: Rotate -90 degrees
        robot.turnCustom(-Math.PI / 2, NORMAL);

        // Step 5: Go forward to 10ft (10 ft * 304.8 mm/ft = 3048 mm)
        robot.forward(3048, NORMAL);

        // Step 6: Rotate -120 degrees
        robot.turnCustom(-Math.toRadians(120), NORMAL);

        // Step 7: Shoot all 4 balls again
        robot.shootOn(robot.currLaunchVel);
        sleep(1000);
        robot.forwardSizzleSteak(1.0);
        sleep(2000);
        robot.sizzleSteakOff();
        robot.shootOff();
    }

}
