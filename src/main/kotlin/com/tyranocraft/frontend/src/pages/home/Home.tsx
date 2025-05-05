import styles from './Home.module.scss';
import SampleButton, {SampleButtonProps} from '@/components/sampleButton/SampleButton';
import {useAuthStore} from "@/store/useAuthStore";

export default function Home() {
    const { isLoggedIn, username, login, logout } = useAuthStore()

    const handleLogin = () => {
        login('수남')
    }

    const handleLogout = () => {
        logout()
    }

    const buttonProps: SampleButtonProps = {
        label: isLoggedIn ? '로그아웃' : '로그인',
        onClick: isLoggedIn ? handleLogout : handleLogin,
    }

    return (
        <div className={styles.homeContainer}>
            <h1>Home 입니다.</h1>

            <p>
                {isLoggedIn
                    ? `안녕하세요, ${username}님!`
                    : '로그인이 필요합니다.'}
            </p>

            <SampleButton label={buttonProps.label} onClick={buttonProps.onClick}/>
        </div>
    );
}
