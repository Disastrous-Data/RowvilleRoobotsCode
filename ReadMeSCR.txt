 
Joystick button configuration
 INTAKE_TOGGLE(JoystickButtons.TRIGGER), works
 INTAKE_READY(JoystickButtons.SIDE_BUTTON), works
 ROLLER_CLAW_UP(JoystickButtons.TOP_LEFT_5), works on spin
 ROLLER_CLAW_DOWN(JoystickButtons.BOTTOM_LEFT_3), works on spin
 GROUND_INTAKE_SWING_UP(JoystickButtons.BOTTOM_RIGHT_4),
 GROUND_INTAKE_SWING_DOWN(JoystickButtons.TOP_RIGHT_6),
 GROUND_INTAKE_SPIN_IN(JoystickButtons.BASE_11), works, change to BASE_8
 GROUND_INTAKE_SPING_OUT(JoystickButtons.BASE_7); works

Motor Configuration
	// Drive Base							PDH		Device ID
        RightMotor1 = new CANSparkMax(1, MotorType.kBrushed); 		19		CAM 1
        RightMotor2 = new CANSparkMax(2, MotorType.kBrushed);		18		CAM 2
        LeftMotor1 = new CANSparkMax(3, MotorType.kBrushed);		0		CAM 3
        LeftMotor2 = new CANSparkMax(4, MotorType.kBrushed);		1		CAM 4

	// Shooter
        TopIntakeMotor = new WPI_TalonSRX(7);				16		Talon 7
        BottomIntakeMotor = new WPI_TalonSRX(5);			17		Talon 5

        RollerClaw = new WPI_TalonSRX(2);				2		Talon 4

        // Ground intake
        GroundIntakeSwing = new CANSparkMax(8, MotorType.kBrushed);	13		CAM 5
        GroundIntakeSpin = new WPI_TalonSRX(12);			12		Talon 2


